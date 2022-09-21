package com.rest.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RequestMapping("/api")
public class PublicController {

    @GetMapping(path = "/public")
    public ResponseEntity<String> publicAPI() {
        return ResponseEntity.ok("PUBLIC API");
    }

}
