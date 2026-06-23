package com.clinical.manager.health.checks.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clinical.manager.health.checks.Config.JwtUtil;
import com.clinical.manager.health.checks.DTO.LoginRequest;
import com.clinical.manager.health.checks.DTO.LoginResponse;
import com.clinical.manager.health.checks.Entity.Users;
import com.clinical.manager.health.checks.Exception.BadRequestException;
import com.clinical.manager.health.checks.Repository.UserRepository;

/*
 * Handles authentication business logic.
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /*
     * Authenticates user login.
     */
    public LoginResponse login(LoginRequest request) {

        /*
         * Find user using email.
         */
        Users user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password")
                );

        /*
         * Compare entered password
         * with encrypted database password.
         */
        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        /*
         * Reject login if password incorrect.
         */
        if (!passwordMatches) {

            throw new BadRequestException(
             "Invalid email or password"
);
        }

        /*
         * Reject disabled accounts.
         */
        if (!user.isActive()) {

            throw new BadRequestException(
    "Invalid email or password"
);
        }

        /*
         * Generate JWT token.
         */
        String token =
                jwtUtil.generateToken(user.getEmail());

        /*
         * Return login response.
         */
        return new LoginResponse(

                true,

                token,

                user.getRole(),

                "Login successful"
        );
    }
}