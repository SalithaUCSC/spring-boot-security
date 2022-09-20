package com.rest.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthTestController {

    @GetMapping(path = "/test/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("admin API");
    }

    @GetMapping(path = "/test/user")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("user API");
    }

}
