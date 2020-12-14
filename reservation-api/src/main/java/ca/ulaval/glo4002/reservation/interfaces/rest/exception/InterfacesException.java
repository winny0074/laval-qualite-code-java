package ca.ulaval.glo4002.reservation.interfaces.rest.exception;

import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.WebApplicationException;

public class InterfacesException extends WebApplicationException {
  private final String error;
  private final String description;
  private final int statusCode;

  public InterfacesException(String error, String description) {
    this.error = error;
    this.description = description;
    this.statusCode = HttpStatus.BAD_REQUEST_400;
  }

  public ExceptionResponse getExceptionResponse() {
    return new ExceptionResponse(error, description, statusCode);
  }
}