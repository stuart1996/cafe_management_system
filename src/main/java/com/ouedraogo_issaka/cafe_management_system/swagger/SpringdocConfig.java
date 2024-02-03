package com.ouedraogo_issaka.cafe_management_system.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SpringdocConfig {

    final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI().info( new Info().title("Cafe Management System")
                .version("1.0.0")
                .description("Une documentation de Cafe Management System")
        )
        .addSecurityItem(new SecurityRequirement()
                    .addList(securitySchemeName))
                .components(new Components()
                    .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));

    }
}