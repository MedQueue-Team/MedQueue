package com.clinical.manager.health.checks.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 * Main Spring Security configuration.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    /*
     * BCrypt password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /*
     * Authentication manager bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }

    /*
     * Main security configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                /*
                 * Connect Spring Security to the global CORS configuration.
                 */
                .cors(cors -> {})

                /*
                 * Disable CSRF for REST APIs.
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * Stateless JWT authentication.
                 */
                .sessionManagement(session ->

                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                /*
                 * Authorization rules.
                 */
   .authorizeHttpRequests(auth -> auth

        // PUBLIC ENDPOINTS
        .requestMatchers(

                "/uploads/**",

                "/api/v1/auth/**",

                "/api/v1/queue/register",

                "/api/v1/patients/register",

                "/api/v1/users/register",

                "/swagger-ui/**",

                "/swagger-ui.html",

                "/v3/api-docs/**",

                "/ws/**",

        "/health",

        "/actuator/**"

        ).permitAll()

       
        // ADMIN ONLY
        .requestMatchers(
                "/api/v1/admin/**"
        ).hasRole("ADMIN")

        // DOCTOR ONLY
        .requestMatchers(
                "/api/v1/doctor/**"
        ).hasRole("DOCTOR")

        // RECEPTIONIST
        .requestMatchers(
                "/api/v1/receptionist/**"
        ).hasAnyRole("RECEPTIONIST", "ADMIN")

        // QUEUE
        .requestMatchers(
                "/api/v1/queue/**"
        ).authenticated()

        // USERS
        .requestMatchers(
                "/api/v1/users/**"
        ).authenticated()

        .anyRequest()
        .authenticated()
)

                /*
                 * Add JWT filter.
                 */
                .addFilterBefore(

                        jwtFilter,

                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
