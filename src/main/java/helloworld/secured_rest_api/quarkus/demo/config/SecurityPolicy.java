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
import jakarta.ws.rs.NotAuthorizedException;
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
            // Only ADMINISTRATOR can request secured endpoints
            Constants.api + Constants.v1 + "/secured");

    @Override
    public String name() {
        return "defaultSecurityPolicy";
    }

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

        // check if path is allowed for ADMINISTRATOR
        isAllowed = ALLOWED_ADMINISTRATOR.stream().anyMatch(requestPath::startsWith);
        if (isAllowed) {
            if (requestHasAuthorityForPath(request, Constants.AUTHORITY_ADMINISTRATOR)) {
                LOGGER.debug("path {} allowed for ADMINISTRATOR", requestPath);
                return CheckResult.permit();
            }
        }

        // TODO - Add your security rules here

        LOGGER.debug("path {} rejected", requestPath);
        return CheckResult.deny();
    }

    /**
     * Check if request contains authority for requested path
     * 
     * @param request HTTP request
     * @param authority authority needed
     * @return true if request contains authority for requested path
     */
    private boolean requestHasAuthorityForPath(HttpServerRequest request, String authority) {

        String token = extractTokenFromRequest(request);

        // Retrieve username and authorities
        SecurityIdentity securityIdentity = _tokenService.getAuthentication(token);
        Set<String> authorities = securityIdentity.getRoles();

        return authorities.contains(authority);
    }

    /**
     * Extract token from HTTP header
     * 
     * @param request HTTP request
     * @return token
     */
    private String extractTokenFromRequest(HttpServerRequest request) {

        // Retrieve JWT token from HTTP header Authorization
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(Constants.KEY_BEARER)
                && bearerToken.length() > 7) {

            // "Bearer token" the token starts at index 7
            String token = bearerToken.substring(7);

            if (token != null && !"".equals(token)) {
                LOGGER.debug("Token has been extracted from Authorization HTTP header");
                return token;
            }
        }

        LOGGER.debug("Error while extracting token from HTTP header");
        throw new NotAuthorizedException("Invalid token");
    }
}
