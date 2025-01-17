package helloworld.secured_rest_api.quarkus.demo.controller;


import org.eclipse.microprofile.openapi.annotations.Operation;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/q/swagger-ui")
public class SwaggerRedirectResource {

    @Operation(hidden = true)
    @GET
    public Response redirectToSwagger() {
        return Response.status(200).header("Location", "/q/swagger-ui/").build();
    }
}
