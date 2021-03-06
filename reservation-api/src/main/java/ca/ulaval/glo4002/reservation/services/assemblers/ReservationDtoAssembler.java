package ca.ulaval.glo4002.reservation.services.assemblers;

import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.customer.CustomerDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ResponseReservationDto;

import java.util.List;

public class ReservationDtoAssembler {
  private final CustomerDtoAssembler customerDtoAssembler;

  public ReservationDtoAssembler() {
    customerDtoAssembler = new CustomerDtoAssembler();
  }

  public ReservationDtoAssembler(CustomerDtoAssembler customerDtoAssembler) {
    this.customerDtoAssembler = customerDtoAssembler;
  }

  public ResponseReservationDto from(Reservation reservation) {
    Money price = reservation.getPrice();
    GloDateTime date = reservation.getDinnerDate();
    List<CustomerDto> customerDtos = customerDtoAssembler.from(reservation.getCustomers());

    return new ResponseReservationDto(
        price.toBigDecimal(), date.toIsoDateTimeFormat(), customerDtos);
  }
}
