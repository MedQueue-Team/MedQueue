package com.clinical.manager.health.checks.Config;


import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/*
 * Handles JWT generation and validation.
 */
@Component
public class JwtUtil {

    /*
     * Secret key used to sign tokens.
     *
     * In production:
     * store securely in environment variables.
     */
    private static final String SECRET_KEY =
            "mysecuresecretkeymysecuresecretkey123456";

    /*
     * Token validity duration.
     * Here: 24 hours.
     */
    private static final long EXPIRATION_TIME =
            1000 * 60 * 60 * 24;

    /*
     * Generates signing key from secret string.
     */
    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /*
     * Generates JWT token using user email.
     */
    public String generateToken(String email) {

        return Jwts.builder()

                // Subject stored inside token
                .setSubject(email)

                // Token creation time
                .setIssuedAt(new Date())

                // Token expiration time
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + EXPIRATION_TIME)
                )

                // Sign token using secret key
                .signWith(getSigningKey(),
                        SignatureAlgorithm.HS256)

                .compact();
    }

    /*
     * Extracts email from token.
     */
    public String extractEmail(String token) {

        return extractClaims(token).getSubject();
    }

    /*
     * Validates token expiration.
     */
    public boolean isTokenValid(String token) {

        return !extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    /*
     * Extracts all token claims.
     */
    private Claims extractClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(getSigningKey())

                .build()

                .parseClaimsJws(token)

                .getBody();
    }
}
