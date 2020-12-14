package ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.reservationException;

import ca.ulaval.glo4002.reservation.interfaces.rest.exception.InterfacesException;

public class InvalidReservationQuantity extends InterfacesException {
  private static final String ERROR = "INVALID_RESERVATION_QUANTITY";
  private static final String DESCRIPTION = "Reservations must include tables and customers";

  public InvalidReservationQuantity() {
    super(ERROR, DESCRIPTION);
  }
}
