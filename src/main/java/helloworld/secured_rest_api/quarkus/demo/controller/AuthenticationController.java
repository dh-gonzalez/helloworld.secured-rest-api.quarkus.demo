package helloworld.secured_rest_api.quarkus.demo.controller;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import helloworld.secured_rest_api.quarkus.demo.common.Constants;
import helloworld.secured_rest_api.quarkus.demo.dto.AuthenticationRequestDTO;
import helloworld.secured_rest_api.quarkus.demo.dto.ErrorDetailsDTO;
import helloworld.secured_rest_api.quarkus.demo.dto.TokenDTO;
import helloworld.secured_rest_api.quarkus.demo.service.AuthenticationService;
import helloworld.secured_rest_api.quarkus.demo.service.TokenService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Controller which manages authentication
 */
@Path(Constants.api)
public class AuthenticationController {

    /** Logger for this class */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ConfigProperty(name = "jwt.expiration", defaultValue = "3600")
    private long _jwtExpiration;

    /** Authantication service */
    @Inject
    private AuthenticationService _authenticationService;

    /** Token service */
    @Inject
    private TokenService _tokenService;

    @Operation(summary = "User authentication",
            description = "Authenticate user with given credentials and provide a token in return")
    @APIResponses({
            //
            @APIResponse(responseCode = "200", description = "Greeting message has been returned"),
            //
            @APIResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorDetailsDTO.class))),
            //
            @APIResponse(responseCode = "500",
                    description = "Unexpected error has occurred while processing request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorDetailsDTO.class)))})
    @POST
    @Path(Constants.v1 + "/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TokenDTO> authenticate(AuthenticationRequestDTO authenticationRequest) {

        LOGGER.info("Authentication has been requested");

        return Uni.createFrom().item(authenticationRequest)
                // Authenticate user
                .map(credentialsDTO -> _authenticationService.authenticate(credentialsDTO))
                // Generate token for user
                .map(securityIdentity -> _tokenService.generateToken(securityIdentity))
                // Map to DTO for caller
                .map(token -> new TokenDTO(token, Constants.KEY_BEARER, _jwtExpiration));
    }
}
