package helloworld.secured_rest_api.quarkus.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import java.util.Collection;
import java.util.Set;
import org.junit.jupiter.api.Test;
import helloworld.secured_rest_api.quarkus.demo.common.Constants;
import helloworld.secured_rest_api.quarkus.demo.service.impl.TokenServiceImpl;
import io.quarkus.security.credential.Credential;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

/**
 * Unit test class of service TokenService
 */
@QuarkusTest
public class TokenServiceTest {

    // The service to be tested
    @Inject
    private TokenServiceImpl tokenService;

    // This token has expired on EPOCH
    String expiredToken =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImdyb3VwcyI6WyJBRE1JTklTVFJBVE9SIl0sImV4cCI6MCwiaWF0IjoxNzQzMTk4NTgxLCJqdGkiOiJmODU3ZmM2NC01YTFkLTQ3YzktODRmYy1lNTIyYTAwYjcyZjIifQ.yxgYQGXkE4PX06HunGIN1LNTKP1uj3j9PAM9lXVj0Hc";

    @Test
    public void test_generateToken_Nominal() {

        // Test data
        SecurityIdentity securityIdentity =
                QuarkusSecurityIdentity.builder().setPrincipal(new QuarkusPrincipal("admin"))
                        .addRole(Constants.AUTHORITY_ADMINISTRATOR).build();

        // Test nominal case
        String token = tokenService.generateToken(securityIdentity);
        assertNotNull(token);
    }

    @Test
    public void test_getAuthentication_Nominal() {

        // Test data
        SecurityIdentity securityIdentity =
                QuarkusSecurityIdentity.builder().setPrincipal(new QuarkusPrincipal("admin"))
                        .addRole(Constants.AUTHORITY_ADMINISTRATOR).build();

        // Test nominal case
        String token = tokenService.generateToken(securityIdentity);
        assertNotNull(token);

        // Check authentication from token
        SecurityIdentity retrievedAuthentication = tokenService.getAuthentication(token);
        assertNotNull(retrievedAuthentication);
        assertEquals("admin", retrievedAuthentication.getPrincipal().getName());
        Set<Credential> credentials = retrievedAuthentication.getCredentials();
        assertNotNull(credentials);
        assertEquals(0, credentials.size());
        Collection<String> retrievedAuthorities = retrievedAuthentication.getRoles();
        assertNotNull(retrievedAuthorities);
        assertEquals(1, retrievedAuthorities.size());
        String retrievedAuthority = retrievedAuthorities.iterator().next();
        assertEquals(Constants.AUTHORITY_ADMINISTRATOR, retrievedAuthority);

        // Test nominal case with no authorities
        securityIdentity = QuarkusSecurityIdentity.builder()
                .setPrincipal(new QuarkusPrincipal("admin")).build();
        token = tokenService.generateToken(securityIdentity);
        assertNotNull(token);

        // Check authentication from token
        retrievedAuthentication = tokenService.getAuthentication(token);
        assertNotNull(retrievedAuthentication);
        assertEquals("admin", retrievedAuthentication.getPrincipal().getName());
        credentials = retrievedAuthentication.getCredentials();
        assertNotNull(credentials);
        assertEquals(0, credentials.size());
        retrievedAuthorities = retrievedAuthentication.getRoles();
        assertNotNull(retrievedAuthorities);
        assertEquals(0, retrievedAuthorities.size());
    }

    @Test
    public void test_getAuthentication_expiredToken() {

        // Test token is expired
        assertThrowsExactly(NotAuthorizedException.class,
                () -> tokenService.getAuthentication(expiredToken));
    }

    @Test
    public void test_validateToken_Exceptions() {

        // Test token is invalid
        assertThrowsExactly(NotAuthorizedException.class,
                () -> tokenService.getAuthentication(null));

        // Test token is invalid
        assertThrowsExactly(NotAuthorizedException.class,
                () -> tokenService.getAuthentication("wrongToken"));
    }
}
