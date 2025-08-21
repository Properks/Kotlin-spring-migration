package org.jeongmo.practice.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    private val securityScheme: String = "JWT"

    @Bean
    fun swagger(): OpenAPI {
        val info = Info().title("Kotlin Practice").description("KP API").version("0.0.1")

        val securityRequirement = SecurityRequirement().addList(securityScheme)

        val components = Components()
            .addSecuritySchemes(
                securityScheme, SecurityScheme()
                    .name(securityScheme)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("Bearer")
                    .bearerFormat("JWT")
            )

        return OpenAPI()
            .info(info)
            .addServersItem(Server().url("/"))
            .addSecurityItem(securityRequirement)
            .components(components)
    }
}