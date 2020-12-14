package ca.ulaval.glo4002.reservation.interfaces.rest.mappers;

import ca.ulaval.glo4002.reservation.domain.exception.DomainException;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.ExceptionResponse;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.DomainExceptionResponseFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DomainExceptionMapper implements ExceptionMapper<DomainException> {
  DomainExceptionResponseFactory domainExceptionResponseFactory = new DomainExceptionResponseFactory();

  @Override
  public Response toResponse(DomainException domainException) {
    ExceptionResponse exceptionResponse = domainExceptionResponseFactory.create(domainException);

    return Response
            .status(exceptionResponse.getStatusCode())
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(exceptionResponse)
            .build();
  }
}
