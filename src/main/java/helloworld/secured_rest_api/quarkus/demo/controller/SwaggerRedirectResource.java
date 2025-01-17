package helloworld.secured_rest_api.quarkus.demo.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/q/swagger-ui")
public class SwaggerRedirectResource {

    @GET
    public Response redirectToSwagger() {
        return Response.status(200).header("Location", "/q/swagger-ui/").build();
    }
}
