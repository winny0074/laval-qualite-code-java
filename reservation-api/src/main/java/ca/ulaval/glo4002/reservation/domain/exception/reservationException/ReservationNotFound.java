package ca.ulaval.glo4002.reservation.domain.exception.reservationException;

import ca.ulaval.glo4002.reservation.domain.exception.DomainException;

public class ReservationNotFound extends DomainException {
  private final String reservationId;

  public ReservationNotFound(String reservationId) {
    this.reservationId = reservationId;
  }

  public String getReservationId() {
    return reservationId;
  }
}
