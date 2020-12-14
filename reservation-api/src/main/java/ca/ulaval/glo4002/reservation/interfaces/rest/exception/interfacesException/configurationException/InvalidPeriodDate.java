package ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.configurationException;

import ca.ulaval.glo4002.reservation.interfaces.rest.exception.InterfacesException;

public class InvalidPeriodDate extends InterfacesException {
  public static final String ERROR = "INVALID_DATE";
  public static final String DESCRIPTION =
    "Invalide dates, please use the format yyyy-mm-dd";

  public InvalidPeriodDate() { super(ERROR, DESCRIPTION); }
}
