package helloworld.secured_rest_api.quarkus.demo.exception;

import helloworld.secured_rest_api.quarkus.demo.dto.ErrorDetailsDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * This class allows to handle global exception and provide the caller an explicit error message
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {

        ErrorDetailsDTO errorDetails =
                new ErrorDetailsDTO(exception.getClass().getSimpleName(), exception.getMessage());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorDetails).build();
    }
}

