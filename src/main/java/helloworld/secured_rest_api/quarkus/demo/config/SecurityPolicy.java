package helloworld.secured_rest_api.quarkus.demo.config;

import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import helloworld.secured_rest_api.quarkus.demo.common.Constants;
import helloworld.secured_rest_api.quarkus.demo.service.TokenService;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.vertx.http.runtime.security.HttpSecurityPolicy;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;

/**
 * Security configuration
 */
@ApplicationScoped
public class SecurityPolicy implements HttpSecurityPolicy {

    /** Logger for this class */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** Token service */
    @Inject
    private TokenService _tokenService;

    // List of allowed URLs
    private static final List<String> ALLOWED_EVERYONE = List.of(
            // Everybody can access to Swagger UI
            // JSON API documentation endpoint
            "/q/openapi",
            // Swagger UI
            "/q/swagger-ui",
            // Everybody can access liveness/readyness probes
            "/q/health",
            // Everybody can request authentication
            Constants.api + Constants.v1 + "/authenticate",
            // Everybody can request non secured endpoints
            Constants.api + Constants.v1 + "/anonymous");

    // List of allowed URLs
    private static final List<String> ALLOWED_ADMINISTRATOR = List.of(
            // Everybody can request non secured endpoints
            Constants.api + Constants.v1 + "/secured");

    @Override
    public Uni<CheckResult> checkPermission(RoutingContext event, Uni<SecurityIdentity> identity,
            AuthorizationRequestContext requestContext) {

        HttpServerRequest request = event.request();
        String requestPath = request.path();

        // Check if path is allowed for everyone
        boolean isAllowed = ALLOWED_EVERYONE.stream().anyMatch(requestPath::startsWith);
        if (isAllowed) {
            LOGGER.debug("path {} allowed for everyone", requestPath);
            return CheckResult.permit();
        }

        // check if path is allowed for ADMINISTRATOR only
        isAllowed = ALLOWED_ADMINISTRATOR.stream().anyMatch(requestPath::startsWith);
        if (isAllowed) {

            // Retrieve JWT token
            String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (bearerToken != null && bearerToken.startsWith(Constants.KEY_BEARER)
                    && bearerToken.length() > 7) {
                // "Bearer token" the token starts at index 7
                String token = bearerToken.substring(7);
                LOGGER.debug("Token has been extracted from Authorization HTTP header");

                // Retrieve username and authorities
                SecurityIdentity securityIdentity = _tokenService.getAuthentication(token);
                String username = securityIdentity.getPrincipal().getName();
                Set<String> authorities = securityIdentity.getRoles();

                if (authorities.contains(Constants.AUTHORITY_ADMINISTRATOR)) {
                    LOGGER.debug("path {} allowed for ADMINISTRATOR {}", requestPath, username);
                    return CheckResult.permit();
                }
            }
        }

        LOGGER.debug("path {} rejected", requestPath);
        return CheckResult.deny();
    }

    @Override
    public String name() {
        return "defaultSecurityPolicy";
    }
}
