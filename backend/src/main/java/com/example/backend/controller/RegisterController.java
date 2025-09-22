package com.example.backend.controller;

import com.example.backend.service.UserPresenter;
import com.example.backend.service.auth.RegistrationService;
import com.example.share.interfaces.dto.RegisterDtoValues;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/register")
@RequiredArgsConstructor
@RestController
public class RegisterController {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> createNewUser(@ModelAttribute() RegisterDtoValues registerDtoValues, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));

            bindingResult.getGlobalErrors().forEach(error ->
                    errors.put("globalError", error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errors);
        }
        return registrationService.createNewUser(registerDtoValues, response, request);
    }
}
