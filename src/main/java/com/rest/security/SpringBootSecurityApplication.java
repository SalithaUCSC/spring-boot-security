package com.rest.security;

import com.rest.security.entities.Role;
import com.rest.security.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class SpringBootSecurityApplication {

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
	}

	@PostConstruct
	public void addRoles() {
		roleRepository.saveAll(
			Arrays.asList(
				Role.builder().name("ROLE_ADMIN").build(),
				Role.builder().name("ROLE_USER").build()
			)
		);
	}

}
