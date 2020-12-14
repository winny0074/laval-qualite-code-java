package ca.ulaval.glo4002.reservation.domain.exception.reservationException;

import ca.ulaval.glo4002.reservation.domain.exception.DomainException;

public class InvalidReservationDate extends DomainException {
  private final String startDate;
  private final String endDate;

  public InvalidReservationDate(String startDate, String endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }
}
