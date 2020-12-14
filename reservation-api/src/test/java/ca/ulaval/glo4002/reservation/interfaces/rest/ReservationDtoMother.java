package ca.ulaval.glo4002.reservation.interfaces.rest;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.country.CountryDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ReservationDto;

import java.util.ArrayList;

public class ReservationDtoMother {
  private static String SOME_ISODATETIMEFORMAT_DATE_STRING = "2150-07-20T00:00:00.000Z";
  public static ReservationDto createBasicReservationDto() {
    ReservationDto dto = new ReservationDto();
    dto.creationDate = SOME_ISODATETIMEFORMAT_DATE_STRING;
    dto.dinnerDate = SOME_ISODATETIMEFORMAT_DATE_STRING;
    dto.country = new CountryDto();
    dto.tableDtos = new ArrayList<>();
    return dto;
  }
}
