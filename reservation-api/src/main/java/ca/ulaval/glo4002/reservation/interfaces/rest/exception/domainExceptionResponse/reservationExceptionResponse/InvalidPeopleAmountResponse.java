package ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.reservationExceptionResponse;

import org.eclipse.jetty.http.HttpStatus;

public class InvalidPeopleAmountResponse {
  public static final String ERROR = "TOO_MANY_PEOPLE";
  public static final String DESCRIPTION =
          "The reservation tries to bring a number of people which does not comply with recent government laws.";
  public static final int STATUS_CODE = HttpStatus.BAD_REQUEST_400;

}
