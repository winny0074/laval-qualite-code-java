package ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.ingredientsReportsException;

import ca.ulaval.glo4002.reservation.interfaces.rest.exception.InterfacesException;

public class InvalidDate extends InterfacesException {
  private static final String ERROR = "INVALID_DATE";
  private static final String DESCRIPTION =
          "Dates should be between %s and %s and must be specified.";

  public InvalidDate(String startDate, String endDate) {
    super(ERROR, String.format(DESCRIPTION, startDate, endDate));
  }
}
