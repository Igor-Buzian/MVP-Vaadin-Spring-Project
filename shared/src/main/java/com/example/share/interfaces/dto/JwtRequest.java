package com.example.share.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for JWT authentication requests.
 * This class encapsulates the email and password provided by a user
 * during the login process, along with validation constraints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    private String email;
    private String password;
    private String captchaResponse;

}