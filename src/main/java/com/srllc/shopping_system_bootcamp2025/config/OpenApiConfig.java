package com.srllc.shopping_system_bootcamp2025.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    /**
     * Configures the OpenAPI documentation for the application.
     * <p>
     * This method initializes an {@link OpenAPI} instance with
     * application metadata including title, description, version,
     * license information, security requirements, and external documentation links.
     * </p>
     *
     * @return An {@link OpenAPI} instance containing the API documentation configuration.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Shopping System for Bootcamp Batch 2025")
                        .version("1.0")
                        .description("""
                                API documentation for Shopping System Service.
                                
                                **Back-End Developers:**
                                - Alexander Thomas Sayson
                                """)
                        .contact(new Contact()
                                .name("Alexander Thomas Sayson")
                                .email("alexander@solutionsresource.com")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name("Authorization")
                                .description("Bearer Token")));
    }

}
