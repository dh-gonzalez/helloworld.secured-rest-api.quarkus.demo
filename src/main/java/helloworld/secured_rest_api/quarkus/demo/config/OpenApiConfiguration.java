package helloworld.secured_rest_api.quarkus.demo.config;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(title = "API Documentation", version = "1.0",
                description = "API Documentation"),
        security = @SecurityRequirement(name = "bearerAuthentication"))
@SecurityScheme(securitySchemeName = "bearerAuthentication", scheme = "bearer",
        type = SecuritySchemeType.HTTP, bearerFormat = "JWT")
public class OpenApiConfiguration extends Application {
    // Nothing to do
}
