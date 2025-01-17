package helloworld.secured_rest_api.quarkus.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import helloworld.secured_rest_api.quarkus.demo.common.Constants;
import helloworld.secured_rest_api.quarkus.demo.dto.AuthenticationRequestDTO;
import helloworld.secured_rest_api.quarkus.demo.service.AuthenticationService;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAuthorizedException;

/**
 * Authentication service implementation
 */
@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {

    /** Logger for this class */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public SecurityIdentity authenticate(AuthenticationRequestDTO authenticationRequest) {

        // TODO - Replace with a real user management<br>
        // This user authentication is for demonstration purpose
        // Replace this by a real user management like LDAP users management for example
        if (!"admin".equalsIgnoreCase(authenticationRequest.getUsername())
                || !"admin".equalsIgnoreCase(authenticationRequest.getPassword())) {

            LOGGER.error("Authentication failed for {}", authenticationRequest.getUsername());

            throw new NotAuthorizedException("Invalid login or password");
        }

        return QuarkusSecurityIdentity.builder()
                //
                .setPrincipal(new QuarkusPrincipal(authenticationRequest.getUsername()))
                //
                .addRole(Constants.AUTHORITY_ADMINISTRATOR)
                //
                .build();
    }
}
