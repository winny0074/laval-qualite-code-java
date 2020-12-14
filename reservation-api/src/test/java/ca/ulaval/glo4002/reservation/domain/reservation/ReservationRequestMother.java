package ca.ulaval.glo4002.reservation.domain.reservation;

import ca.ulaval.glo4002.reservation.domain.customer.CustomerRequest;
import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;

import java.util.Collections;
import java.util.List;

public class ReservationRequestMother {
  public static ReservationRequest createBasicRequest() {
    ReservationRequest request = new ReservationRequest();
    request.vendorCode = "VENDOR";
    request.dinnerDate = GloDateTime.ofLocalDateFormat("2150-07-20");
    request.reservationDate = GloDateTime.ofLocalDateFormat("2150-01-20");
    request.countryCurrency = "FKD";
    request.countryName = "Fake country";
    request.countryCode = "FAK";
    request.tables = List.of(List.of(makePlainCustomerRequest()));
    return request;
  }

  public static ReservationRequest createBasicRequestForTwo() {
    ReservationRequest request = createBasicRequest();
    request.tables = List.of(List.of(makePlainCustomerRequest(), makePlainCustomerRequest()));
    return request;
  }

  private static CustomerRequest makePlainCustomerRequest() {
    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.name = "John Doe";
    customerRequest.restrictions = Collections.emptyList();
    return customerRequest;
  }

  public static ReservationRequest createBasicRequestWithAllergies() {
    ReservationRequest request = createBasicRequest();
    request.tables = List.of(List.of(makePlainCustomerRequest(), makeAllergicCustomerRequest()));
    return request;
  }

  private static CustomerRequest makeAllergicCustomerRequest() {
    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.name = "John Doe";
    customerRequest.restrictions = List.of(Restriction.ALLERGIE.toString());
    return customerRequest;
  }
}
