package com.clinical.manager.health.checks.Config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Configures Swagger/OpenAPI
 * documentation.
 */
@Configuration
public class OpenApiConfig {

    /*
     * Creates API documentation configuration.
     */
    @Bean
    public OpenAPI customOpenAPI() {

        /*
         * JWT security scheme.
         */
        SecurityScheme securityScheme =
                new SecurityScheme()

                        .type(SecurityScheme.Type.HTTP)

                        .scheme("bearer")

                        .bearerFormat("JWT");

        return new OpenAPI()

                /*
                 * API information.
                 */
                .info(new Info()

                        .title(
                                "AI Clinic Queue Manager API"
                        )

                        .description(
                                "Professional role-based clinic queue system backend."
                        )

                        .version("1.0")

                        .contact(new Contact()

                                .name(
                                        "AI Clinic Team"
                                )

                                .email(
                                        "clinic@example.com"
                                )
                        )

                        .license(new License()

                                .name("MIT License")
                        )
                )

                /*
                 * Add JWT authentication support.
                 */
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("Bearer Authentication")
                )

                .components(
                        new Components()

                                .addSecuritySchemes(

                                        "Bearer Authentication",

                                        securityScheme
                                )
                );
    }
}
