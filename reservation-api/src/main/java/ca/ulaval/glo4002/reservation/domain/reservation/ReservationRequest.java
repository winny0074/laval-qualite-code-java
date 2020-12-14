package ca.ulaval.glo4002.reservation.domain.reservation;

import ca.ulaval.glo4002.reservation.domain.customer.CustomerRequest;
import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;

import java.util.Collection;

public class ReservationRequest {
  public String vendorCode;
  public GloDateTime reservationDate;
  public GloDateTime dinnerDate;
  public Collection<Collection<CustomerRequest>> tables;
  public String countryCode;
  public String countryName;
  public String countryCurrency;
}
