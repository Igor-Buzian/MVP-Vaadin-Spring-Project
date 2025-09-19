package com.example.backend.filter;

import com.example.backend.repository.UserDaoRepository;
import com.example.backend.utils.JwtTokenUtils;
import com.example.core.entity.User;
import com.example.core.interfaces.UserDao;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JwtTokenUtils jwtTokenUtils;
    private final UserDao userDao;

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "Auth_cookie".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        logger.debug("Processing JwtAuthFilter for URI: {}", requestURI);

        String token = getToken(request);

        if (token != null) {
            try {
                if (jwtTokenUtils.validateToken(token)) {
                    String userEmail = jwtTokenUtils.getLogin(token);
                    Optional<User> optionalUser = userDao.getByEmail(userEmail);

                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        List<String> rolesFromToken = jwtTokenUtils.getRoleFromToken(token);

                        // Создаем authorities из ролей
                        List<SimpleGrantedAuthority> authorities = rolesFromToken.stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList();

                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                user, null, authorities
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        logger.debug("Authentication set for user: {}", userEmail);
                    } else {
                        logger.warn("User not found with email: {}", userEmail);
                    }
                } else {
                    logger.warn("Invalid JWT token for URI: {}", requestURI);
                }
            } catch (Exception e) {
                logger.error("Error processing JWT token for URI {}: {}", requestURI, e.getMessage(), e);
            }
        } else {
            logger.debug("No JWT token found in cookies for URI: {}", requestURI);
        }

        filterChain.doFilter(request, response);
    }
}
