package com.rest.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping(path = "/test1")
    public ResponseEntity<String> testApi1() {
        return ResponseEntity.ok("ADMIN API 1");
    }

    @GetMapping(path = "/test2")
    public ResponseEntity<String> testApi2() {
        return ResponseEntity.ok("ADMIN API 2");
    }

}
