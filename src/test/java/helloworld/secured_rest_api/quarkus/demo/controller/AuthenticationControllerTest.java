package helloworld.secured_rest_api.quarkus.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import helloworld.secured_rest_api.quarkus.demo.common.Constants;
import helloworld.secured_rest_api.quarkus.demo.dto.AuthenticationRequestDTO;
import helloworld.secured_rest_api.quarkus.demo.dto.TokenDTO;
import helloworld.secured_rest_api.quarkus.demo.service.AuthenticationService;
import helloworld.secured_rest_api.quarkus.demo.service.TokenService;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

/**
 * Unit test class of controller AuthenticationController
 */
@QuarkusTest
public class AuthenticationControllerTest {

    // The services to be mocked
    @InjectMock
    private AuthenticationService authenticationService;

    @InjectMock
    private TokenService tokenService;

    // The controller to be tested
    @Inject
    private AuthenticationController authenticationController;

    @Test
    public void test_Authenticate_Nominal() {

        // Test data
        SecurityIdentity securityIdentity =
                QuarkusSecurityIdentity.builder().setPrincipal(new QuarkusPrincipal("admin"))
                        .addRole(Constants.AUTHORITY_ADMINISTRATOR).build();
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();
        authenticationRequestDTO.setUsername("admin");
        authenticationRequestDTO.setPassword("admin");

        // Mocks behaviour
        when(authenticationService.authenticate(authenticationRequestDTO))
                .thenReturn(securityIdentity);
        when(tokenService.generateToken(securityIdentity)).thenReturn("token");

        // Call service method to test
        Uni<TokenDTO> response = authenticationController.authenticate(authenticationRequestDTO);
        // Retrieve asynchronous result and check expected result
        TokenDTO responseTokenDTO = response.await().indefinitely();
        assertEquals("token", responseTokenDTO.getToken());
        assertEquals(Constants.KEY_BEARER, responseTokenDTO.getType());
        assertEquals(3600, responseTokenDTO.getExpiresInSeconds());

        // Check mocks have been correctly called
        verify(authenticationService).authenticate(authenticationRequestDTO);
        verify(tokenService).generateToken(securityIdentity);
    }
}
