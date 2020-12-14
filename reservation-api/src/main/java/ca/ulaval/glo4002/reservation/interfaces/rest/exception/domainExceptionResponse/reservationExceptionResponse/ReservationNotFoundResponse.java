package ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.reservationExceptionResponse;

import org.eclipse.jetty.http.HttpStatus;

public class ReservationNotFoundResponse {
  public static final String ERROR = "RESERVATION_NOT_FOUND";
  public static final String DESCRIPTION = "Reservation with number %s not found";
  public static final int STATUS_CODE = HttpStatus.NOT_FOUND_404;
}
