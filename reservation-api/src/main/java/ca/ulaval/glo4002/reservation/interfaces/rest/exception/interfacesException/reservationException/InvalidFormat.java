package ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.reservationException;

import ca.ulaval.glo4002.reservation.interfaces.rest.exception.InterfacesException;

public class InvalidFormat extends InterfacesException {
  private static final String ERROR = "INVALID_FORMAT";
  private static final String DESCRIPTION = "Invalid Format";

  public InvalidFormat() {
    super(ERROR, DESCRIPTION);
  }
}
