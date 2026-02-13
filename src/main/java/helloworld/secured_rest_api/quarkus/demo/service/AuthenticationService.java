package helloworld.secured_rest_api.quarkus.demo.service;

import helloworld.secured_rest_api.quarkus.demo.dto.AuthenticationRequestDTO;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.ws.rs.NotAuthorizedException;

/**
 * Authentication service
 */
public interface AuthenticationService {

    /**
     * Authenticate
     * 
     * @param authenticationRequest authentication request
     * @return
     * 
     * @throws NotAuthorizedException if authentication fails
     */
    SecurityIdentity authenticate(AuthenticationRequestDTO authenticationRequest);
}
