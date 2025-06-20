package com.backend.juvaro.security;

import lombok.Data;

@Data
public class AuthCredentials {
    private Long id;
    private String email;
    private String password;
    private String role;
    private int cedula;
}
