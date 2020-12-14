package ca.ulaval.glo4002.reservation.services.assemblers;

import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ResponseReservationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReservationDtoAssemblerTest {
  private static final GloDateTime DINNER_DATE = mock(GloDateTime.class);
  private static final double SOME_AMOUNT = 123.456;
  private static final String ANY_CURRENCY = "JKD";
  private final ReservationDtoAssembler reservationDtoAssembler = new ReservationDtoAssembler();
  private Reservation reservation;
  private Money money;

  @BeforeEach
  public void setUp() {
    money = new Money(SOME_AMOUNT, ANY_CURRENCY);
    reservation = mock(Reservation.class);
    when(reservation.getDinnerDate()).thenReturn(DINNER_DATE);
  }

  @Test
  public void givenReservation_whenCreateDtoFromReservation_shouldMapPriceCorrectly() {
    when(reservation.getPrice()).thenReturn(money);

    ResponseReservationDto dto = reservationDtoAssembler.from(reservation);

    assertEquals(money.toBigDecimal(), dto.reservationPrice);
  }
}
