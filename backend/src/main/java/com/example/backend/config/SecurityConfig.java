package com.example.backend.config;


import com.example.backend.fillter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for the application.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;


    /**
     * Provides a BCryptPasswordEncoder bean for password hashing.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @param http The HttpSecurity object to configure.
     * @return A SecurityFilterChain instance.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .headers(headers -> headers
                        .defaultsDisabled()
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/api/users",
                                "/api/users**",
                                "/api/users/**",
                                "/login",
                                "/login**",
                                "/register",
                                "/register**",
                                "/auth/v1/**",
                                "/error",
                                "/h2-console",
                                "/h2-console**",
                                "/h2-console/**"
                        ).permitAll()
                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    /*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .headers(headers -> headers
                        .defaultsDisabled() // отключает все заголовки по умолчанию
                        .frameOptions(frame -> frame.sameOrigin()) // разрешаем iframe для H2 консоли
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN") // Только /admin требует роль
                        .anyRequest().permitAll() // Всё остальное разрешено
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }*/
}