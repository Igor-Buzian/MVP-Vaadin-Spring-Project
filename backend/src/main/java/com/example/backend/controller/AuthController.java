package com.example.backend.controller;

import com.example.backend.service.auth.AuthService;
import com.example.share.interfaces.dto.JwtRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    /**
     * Handles user authentication requests.
     * Attempts to authenticate the user based on the provided credentials.
     *
     * @param jwtRequest        The DTO containing user login credentials.
     * @param response          The HttpServletResponse.
     * @param request           The HttpServletRequest.
     * @return A ResponseEntity containing the authentication result (e.g., JWT token) or an error.
     */
    @PostMapping
    public ResponseEntity<?> auth(JwtRequest jwtRequest, HttpServletResponse response, HttpServletRequest request)
    {
        return authService.authUser(jwtRequest, request,response);
    }

    /**
     * Retrieves the login attempts for the authentication.
     *
     * @return A ResponseEntity containing information about login attempts.
     */
    @GetMapping("/login-attempts")
    public ResponseEntity<?> getLoginAttempts() {
        return authService.getLoginAttempts();
    }

    /**
     * Handles user logout requests.
     * Invalidates the user's token.
     *
     * @param response The HttpServletResponse.
     * @return A ResponseEntity indicating the success or failure of the logout.
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response)
    {
        return authService.logout(response);
    }

}
