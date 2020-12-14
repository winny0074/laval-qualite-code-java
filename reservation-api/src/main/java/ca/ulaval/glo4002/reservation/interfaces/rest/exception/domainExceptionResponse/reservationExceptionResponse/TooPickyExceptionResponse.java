package ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.reservationExceptionResponse;

import org.eclipse.jetty.http.HttpStatus;

public class TooPickyExceptionResponse {
  public static final String ERROR = "TOO_PICKY";
  public static final String DESCRIPTION =
          "You seem to be too picky and now, you cannot make a reservation for this date.";
  public static final int STATUS_CODE = HttpStatus.BAD_REQUEST_400;

}
