package com.example.share.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object (DTO) for user registration.
 * This class encapsulates the data required to register a new user,
 * including validation constraints for each field.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDtoValues {
    private String username;
    private String email;
    private String password;
    private String ConfirmPassword;
    private String captchaResponse;
}
