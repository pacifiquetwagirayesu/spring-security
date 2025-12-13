package com.pacifique.security.review.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Paccy",
                        email = "dev@commitlink.org",
                        url = "https://commitlink.org"
                ),
                description = "OpenApi documentation for spring security",
                title = "OpenApi specification - Spring Security",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://url.com"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(description = "local env", url = "http://localhost"),
                @Server(description = "Test env", url = "https://dev.example.org")
        },
        security = {
                @SecurityRequirement(name = "bearerAuth"),
                @SecurityRequirement(name = "oauth2")
        }
)

@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = "bearerAuth",
                        description = "JWT Authentication - Paste your token directly (e.g., eyJhbGc...)",
                        scheme = "bearer",
                        type = SecuritySchemeType.HTTP,
                        bearerFormat = "JWT",
                        in = SecuritySchemeIn.HEADER
                ),

                @SecurityScheme(
                        name = "oauth2",
                        description = "OAuth2 Authorization Code Flow",
                        type = SecuritySchemeType.OAUTH2,
                        in = SecuritySchemeIn.HEADER,
                        flows = @OAuthFlows(
                                authorizationCode = @OAuthFlow(
                                        authorizationUrl = "http://localhost:7070/oauth2/authorize",
                                        tokenUrl = "http://localhost:7070/oauth2/token",
                                        refreshUrl = "http://localhost:7070/oauth2/token",
                                        scopes = {
                                                @OAuthScope(name = "read", description = "Read access"),
                                                @OAuthScope(name = "write", description = "Write access"),
                                                @OAuthScope(name = "profile", description = "User profile"),
                                                @OAuthScope(name = "email", description = "User email"),
                                                @OAuthScope(name = "openid", description = "OpenID Connect")
                                        }
                                )
                        )
                )
        }
)

public class OpenApiConfig {

    // Oauth authentication not completed
}
