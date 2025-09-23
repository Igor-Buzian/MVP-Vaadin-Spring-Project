package com.example.backend.service.auth;


import com.example.backend.service.UserPresenter;
import com.example.backend.utils.JwtTokenUtils;
import com.example.core.entity.Role;
import com.example.core.entity.User;
import com.example.core.interfaces.RoleRepository;
import com.example.share.interfaces.dto.RegisterDtoValues;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

/**
 * Service class responsible for handling the creation of new user accounts.
 * This includes validation, password encoding, role assignment, and JWT token generation.
 */
@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RoleRepository roleRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtTokenUtils jwtTokenUtils;
    private  final LoginAttemptService loginAttemptService;
    private final UserPresenter userPresenter;
    /**
     * Creates a new user account based on the provided registration data.
     * Performs checks for existing email, validates password confirmation, assigns a default role,
     * generates a JWT token, and sets it as an HttpOnly cookie.
     *
     * @param registerDtoValues The DTO containing the new user's registration details.
     * @param response The HttpServletResponse to add the JWT cookie to.
     * @param request The HttpServletRequest to get client IP.
     * @return A ResponseEntity indicating the result of the user creation, typically a redirect on success or an error.
     */
    public ResponseEntity<?> createNewUser(RegisterDtoValues registerDtoValues, HttpServletResponse response, HttpServletRequest request){

        try {
           /* if(userPresenter.existsByEmail(registerDtoValues.getEmail())){
                String ip = request.getRemoteAddr();
                if(loginAttemptService.isBloked(ip))
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).header("Location", "/register?error=ip_banned").build();
                if(!loginAttemptService.validateCaptcha(ip, registerDtoValues.getCaptchaResponse()))
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).header("Location", "/register?error=ip_banned").build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).header("Location","http://localhost:8080/register?error=exist_mail").build();
            }*/

            User user = new User();
            user.setEmail(registerDtoValues.getEmail());
            user.setName(registerDtoValues.getUsername());
            user.setPassword(passwordEncoder.encode(registerDtoValues.getPassword()));

         /*   if(!registerDtoValues.getPassword().equals(registerDtoValues.getConfirmPassword()))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).header("Location","http://localhost:8080/register?error=different_password").build();
*/
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new IllegalStateException("Default role 'ROLE_USER' not found in database! Please ensure it's inserted."));


            user.setRoles(new HashSet<>(Collections.singletonList(defaultRole)));
            String token = jwtTokenUtils.generateToken(user);
            userPresenter.createUserEntity(user);

            Cookie cookie = new Cookie("Auth_cookie", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "http://localhost:8080");
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }  
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}