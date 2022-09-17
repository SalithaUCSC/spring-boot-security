package com.rest.security.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class JwtRegisterRequest {
    String name;
    String username;
    String email;
    Set<String> role;
    String password;
}
