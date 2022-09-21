package com.rest.security.controllers;

import com.rest.security.dtos.JwtLoginRequest;
import com.rest.security.dtos.JwtRegisterRequest;
import com.rest.security.dtos.JwtResponse;
import com.rest.security.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody JwtLoginRequest loginRequest) {
        return ResponseEntity.ok().body(authService.loginByUsername(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody JwtRegisterRequest signUpRequest) {
        return ResponseEntity.ok().body(authService.registerUserWithToken(signUpRequest));
    }

}
