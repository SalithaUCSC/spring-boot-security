package com.rest.security.services;

import com.rest.security.configs.jwt.JwtProvider;
import com.rest.security.dtos.JwtLoginRequest;
import com.rest.security.dtos.JwtRegisterRequest;
import com.rest.security.dtos.JwtResponse;
import com.rest.security.entities.Role;
import com.rest.security.entities.RoleType;
import com.rest.security.entities.User;
import com.rest.security.repositories.RoleRepository;
import com.rest.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    public User registerUserWithToken(JwtRegisterRequest signUpRequest) {
        Optional<User> userByUsername = userRepository.findByUsername(signUpRequest.getUsername());
        Optional<User> userByEmail = userRepository.findByEmail(signUpRequest.getEmail());
        if (userByUsername.isPresent()) {
            throw new RuntimeException("Username taken");
        } else if (userByEmail.isPresent()) {
            throw new RuntimeException("Email taken");
        } else {
            return registerUser(signUpRequest);
        }
    }

    private User registerUser(JwtRegisterRequest signUpRequest) {
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> reqRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (signUpRequest.getRole() == null) {
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("USER Role Not Found"));
            roles.add(userRole);
        } else {
            List<String> rolesList = reqRoles.stream().map(String::toUpperCase).collect(Collectors.toList());
            rolesList.forEach(role -> {
                if (RoleType.ROLE_ADMIN.toString().equals(role)) {
                    Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("ADMIN Role Not Found"));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("USER Role Not Found"));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return userRepository.findByUsername(signUpRequest.getUsername()).orElse(null);
    }

    public JwtResponse loginByUsername(JwtLoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if (user.isPresent()) {
            return authenticateUser(loginRequest);
        } else {
            throw new RuntimeException("USER Not Found");
        }
    }

    private JwtResponse authenticateUser(JwtLoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
    }

}
