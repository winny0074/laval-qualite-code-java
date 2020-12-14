package ca.ulaval.glo4002.reservation.domain.exception.ingredientsReportsException;

import ca.ulaval.glo4002.reservation.domain.exception.DomainException;

public class InvalidDate extends DomainException {
  private final String startDate;
  private final String endDate;

  public InvalidDate(String startDate, String endDate) {
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
