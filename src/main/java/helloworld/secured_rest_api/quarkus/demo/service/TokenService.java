package helloworld.secured_rest_api.quarkus.demo.service;

import io.quarkus.security.identity.SecurityIdentity;

/**
 * Token service
 */
public interface TokenService {

    /**
     * Generate a token for authenticated user
     * 
     * @param securityIdentity authenticated user
     * @return token
     */
    String generateToken(SecurityIdentity securityIdentity);

    /**
     * Retrieve authentication from token
     * 
     * @param token token
     * @return authentication from token
     */
    SecurityIdentity getAuthentication(String token);
}
