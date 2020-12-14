package ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.ingredientsReportsException;

import ca.ulaval.glo4002.reservation.interfaces.rest.exception.InterfacesException;

public class InvalidType extends InterfacesException {
  private static final String ERROR = "INVALID_TYPE";
  private static final String DESCRIPTION =
          "Type must be either total or unit and must be specified.";

  public InvalidType() {
    super(ERROR, DESCRIPTION);
  }
}