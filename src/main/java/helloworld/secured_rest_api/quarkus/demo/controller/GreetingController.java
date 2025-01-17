package helloworld.secured_rest_api.quarkus.demo.controller;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import helloworld.secured_rest_api.quarkus.demo.common.Constants;
import helloworld.secured_rest_api.quarkus.demo.dto.ErrorDetailsDTO;
import helloworld.secured_rest_api.quarkus.demo.dto.GreetingDTO;
import helloworld.secured_rest_api.quarkus.demo.service.GreetingService;
import io.quarkus.vertx.http.security.AuthorizationPolicy;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

/**
 * This is an example of REST controller layer
 */
@Path(Constants.api)
public class GreetingController {

    /** Logger for this class */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** Greeting service */
    @Inject
    private GreetingService _greetingService;

    @Operation(summary = "Return an anonymous greeting message",
            description = "Return 'Hello, World!' greeting message")
    @APIResponses({
            //
            @APIResponse(responseCode = "200", description = "Greeting message has been returned"),
            //
            @APIResponse(responseCode = "500",
                    description = "Unexpected error has occurred while processing request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorDetailsDTO.class)))})
    @GET
    @Path(Constants.v1 + "/anonymous/greeting")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<GreetingDTO> greeting() {

        LOGGER.info("Non secured greeting has been requested");

        return Uni.createFrom().item(_greetingService.greeting());
    }

    @Operation(summary = "Return a secured greeting message",
            description = "Return 'Hello, {name}!' greeting message")
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
    @GET
    @AuthorizationPolicy(name = "defaultSecurityPolicy")
    @SecurityRequirement(name = "bearerAuthentication")
    @Path(Constants.v1 + "/secured/greeting")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<GreetingDTO> greeting(
            @Parameter(description = "name", example = "David") @QueryParam("name") String name) {

        LOGGER.info("Secured greeting has been requested for {}", name);

        return Uni.createFrom().item(_greetingService.greeting(name));
    }
}
