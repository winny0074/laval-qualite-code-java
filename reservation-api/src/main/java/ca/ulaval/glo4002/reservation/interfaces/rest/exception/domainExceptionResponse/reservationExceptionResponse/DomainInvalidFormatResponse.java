package ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.reservationExceptionResponse;

import org.eclipse.jetty.http.HttpStatus;

public class DomainInvalidFormatResponse {
  public static final String ERROR = "INVALID_FORMAT";
  public static final String DESCRIPTION = "Invalid Format";
  public static final int STATUS_CODE = HttpStatus.BAD_REQUEST_400;
}
