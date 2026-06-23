package com.clinical.manager.health.checks.Config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * JWT authentication filter.
 *
 * Intercepts incoming requests,
 * validates JWT tokens,
 * and sets authenticated users
 * into Spring Security context.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(

            @NonNull HttpServletRequest request,

            @NonNull HttpServletResponse response,

            @NonNull FilterChain filterChain)

            throws ServletException, IOException {

        /*
         * Current request path.
         */
        String path = request.getServletPath();

        // =====================================================
        // PUBLIC ENDPOINTS
        // Skip JWT validation
        // =====================================================
     if (

        path.startsWith("/api/v1/auth")

                || path.startsWith("/api/v1/users/register")

                || path.startsWith("/api/v1/patients/register")

                || path.startsWith("/api/v1/queue/register")

                || path.startsWith("/uploads")

                || path.startsWith("/swagger-ui")

                || path.startsWith("/v3/api-docs")

                || path.startsWith("/ws")

                || path.startsWith("/health")

                || path.startsWith("/actuator")

) {

    filterChain.doFilter(request, response);

    return;
}

        // =====================================================
        // GET AUTHORIZATION HEADER
        // =====================================================
        String authHeader =
                request.getHeader("Authorization");

        String token = null;

        String email = null;

        /*
         * Extract token from:
         *
         * Authorization: Bearer <token>
         */
        if (

                authHeader != null

                        && authHeader.startsWith("Bearer ")

        ) {

            token = authHeader.substring(7);

            try {

                /*
                 * Extract email from token.
                 */
                email = jwtUtil.extractEmail(token);

            } catch (Exception exception) {

                /*
                 * Invalid token.
                 *
                 * Continue filter chain
                 * without authentication.
                 */
                filterChain.doFilter(request, response);

                return;
            }
        }

        // =====================================================
        // AUTHENTICATE USER
        // =====================================================
        if (

                email != null

                        && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null

        ) {

            /*
             * Load user from database.
             */
            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(email);

            /*
             * Validate token.
             */
            if (jwtUtil.isTokenValid(token)) {

                /*
                 * Create authentication token.
                 */
                UsernamePasswordAuthenticationToken authToken =

                        new UsernamePasswordAuthenticationToken(

                                userDetails,

                                null,

                                userDetails.getAuthorities()
                        );

                /*
                 * Attach request details.
                 */
                authToken.setDetails(

                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                /*
                 * Set authenticated user.
                 */
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        // =====================================================
        // CONTINUE REQUEST
        // =====================================================
        filterChain.doFilter(request, response);
    }
}
