package com.rest.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class AuthTestController {

    @GetMapping(path = "/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> adminAPI() {
        return ResponseEntity.ok("ADMIN API");
    }

    @GetMapping(path = "/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> userAPI() {
        return ResponseEntity.ok("USER API");
    }

    @GetMapping(path = "/public")
    public ResponseEntity<String> publicAPI() {
        return ResponseEntity.ok("PUBLIC API");
    }

}
