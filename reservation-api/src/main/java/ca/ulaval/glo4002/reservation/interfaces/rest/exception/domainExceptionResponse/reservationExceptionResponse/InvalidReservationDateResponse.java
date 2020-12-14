package ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.reservationExceptionResponse;

import org.eclipse.jetty.http.HttpStatus;

public class InvalidReservationDateResponse {
  public static final String ERROR = "INVALID_RESERVATION_DATE";
  public static final String DESCRIPTION =
          "Reservation date should be between %s and %s";
  public static final int STATUS_CODE = HttpStatus.BAD_REQUEST_400;
}
