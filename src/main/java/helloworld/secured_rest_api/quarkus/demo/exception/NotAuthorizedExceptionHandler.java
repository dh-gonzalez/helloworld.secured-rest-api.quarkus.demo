package helloworld.secured_rest_api.quarkus.demo.exception;

import helloworld.secured_rest_api.quarkus.demo.dto.ErrorDetailsDTO;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * This class allows to handle NotAuthorizedException and provide the caller an explicit error
 * message
 */
@Provider
public class NotAuthorizedExceptionHandler implements ExceptionMapper<NotAuthorizedException> {

    @Override
    public Response toResponse(NotAuthorizedException exception) {

        ErrorDetailsDTO errorDetails =
                new ErrorDetailsDTO(exception.getClass().getSimpleName(), exception.getMessage());

        return Response.status(Response.Status.UNAUTHORIZED).entity(errorDetails).build();
    }
}

