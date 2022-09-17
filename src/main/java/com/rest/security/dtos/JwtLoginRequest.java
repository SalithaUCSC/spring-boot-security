package com.rest.security.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtLoginRequest implements Serializable {
    String username;
    String password;
}
