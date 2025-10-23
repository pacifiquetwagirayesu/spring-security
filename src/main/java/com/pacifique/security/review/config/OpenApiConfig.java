package com.pacifique.security.review.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
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
                title = "OpenApi specification - Paccy",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://url.com"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(description = "local env", url = "http://localhost:8080"),
                @Server(description = "Prod env", url = "https://dev.commitlink.org")
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)


@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = "Bearer JWT Auth",
                        description = "JWT auth description",
                        scheme = "bearer",
                        type = SecuritySchemeType.HTTP,
                        bearerFormat = "JWT",
                        in = SecuritySchemeIn.HEADER
                ),

                @SecurityScheme(
                        name = "OAuth 2",
                        description = "OAUTH 2 description",
                        scheme = "bearer",
                        type = SecuritySchemeType.OAUTH2,
                        in = SecuritySchemeIn.HEADER,
                        flows = @OAuthFlows(
                                authorizationCode = @OAuthFlow(
                                        authorizationUrl = "http://localhost:7070/oauth2/authorize",
                                        tokenUrl = "http://localhost:7070/oauth2/token",
                                        scopes = {
                                                @OAuthScope(name = "profile", description = "user profile"),
                                                @OAuthScope(name = "email", description = "user Email"),
                                                @OAuthScope(name = "openId", description = "user details info")
                                        },
                                        extensions = {
                                                @Extension(
                                                        name = "dd",
                                                        properties = @ExtensionProperty(name = "1", value = "pp", parseValue = true))

                                        }
                                )
                        )
                )
        }
)

public class OpenApiConfig {

    // Oauth authentication not completed
}
