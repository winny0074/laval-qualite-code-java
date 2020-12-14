package ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.reservationExceptionResponse;

import org.eclipse.jetty.http.HttpStatus;

public class InvalidDinnerDateResponse {
  public static final String ERROR = "INVALID_DINNER_DATE";
  public static final String DESCRIPTION =
          "Dinner date should be between %s and %s";
  public static final int STATUS_CODE = HttpStatus.BAD_REQUEST_400;
}
