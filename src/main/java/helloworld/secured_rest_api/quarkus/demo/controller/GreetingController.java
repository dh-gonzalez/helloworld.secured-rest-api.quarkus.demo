package helloworld.secured_rest_api.quarkus.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import helloworld.secured_rest_api.quarkus.demo.common.Constants;
import helloworld.secured_rest_api.quarkus.demo.common.dto.GreetingDTO;
import helloworld.secured_rest_api.quarkus.demo.service.GreetingService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path(Constants.api)
public class GreetingController {

    /** Logger for this class */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** Greeting service */
    @Inject
    private GreetingService _greetingService;

    @GET
    @Path(Constants.v1 + "/anonymous/greeting")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<GreetingDTO> hello() {

        LOGGER.info("Non secured greeting has been requested");

        return Uni.createFrom().item(_greetingService.greeting());
    }
}
