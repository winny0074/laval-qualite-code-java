package ca.ulaval.glo4002.reservation.interfaces.rest.mappers;

import ca.ulaval.glo4002.reservation.interfaces.rest.exception.ExceptionResponse;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.InterfacesException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InterfacesExceptionMapper implements ExceptionMapper<InterfacesException> {

  @Override
  public Response toResponse(InterfacesException interfacesException) {
    ExceptionResponse exceptionResponse = interfacesException.getExceptionResponse();
    return Response
            .status(exceptionResponse.getStatusCode())
            .entity(exceptionResponse)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
  }
}