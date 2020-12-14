package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.RoundDownAtTwoDigitsSerializer;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.customer.CustomerDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.List;

public class ResponseReservationDto {

  public final BigDecimal reservationPrice;
  public final String dinnerDate;
  public final List<CustomerDto> customers;

  public ResponseReservationDto(
      @JsonProperty(value = "reservationPrice") @JsonSerialize(using = RoundDownAtTwoDigitsSerializer.class)
          BigDecimal reservationPrice,
      @JsonProperty(value = "dinnerDate") String dinnerDate,
      @JsonProperty(value = "customers") List<CustomerDto> customers) {
    this.reservationPrice = reservationPrice;
    this.dinnerDate = dinnerDate;
    this.customers = customers;
  }
}
