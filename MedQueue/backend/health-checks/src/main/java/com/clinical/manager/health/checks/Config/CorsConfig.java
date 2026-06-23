package com.clinical.manager.health.checks.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
 * Global CORS configuration for REST API requests.
 */
@Configuration
public class CorsConfig {

    /*
     * Shared CORS source used by Spring MVC and Spring Security.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        /*
         * Allow local frontend clients and Android emulator host addresses.
         */
        configuration.setAllowedOriginPatterns(
                List.of(
                        "http://localhost:*",
                        "http://127.0.0.1:*",
                        "http://10.0.2.2:*",
                        "http://10.0.3.2:*",
                        "http://192.168.*:*"
                )
        );

        /*
         * Allow the HTTP methods used by the Android app and web frontend.
         */
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        /*
         * Allow JWT Authorization headers and common request headers.
         */
        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type",
                        "Accept",
                        "Origin",
                        "X-Requested-With"
                )
        );

        /*
         * Let clients read refreshed JWT tokens returned in the response header.
         */
        configuration.setExposedHeaders(
                List.of("Authorization")
        );

        /*
         * Allow authenticated requests that include credentials.
         */
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        /*
         * Apply this CORS policy to every endpoint.
         */
        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}
