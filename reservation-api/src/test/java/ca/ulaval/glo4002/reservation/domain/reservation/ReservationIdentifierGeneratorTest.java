package ca.ulaval.glo4002.reservation.domain.reservation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ReservationIdentifierGeneratorTest {
  private ReservationIdentifierGenerator reservationNumberGenerator =
      ReservationIdentifierGenerator.getInstance();

  @Test
  public void whenGetNextReservationNumberTwice_thenResultShouldBeDifferent() {
    long firstReservationNumber = reservationNumberGenerator.getNextSequenceNumber();
    long secondReservationNumber = reservationNumberGenerator.getNextSequenceNumber();

    assertNotEquals(firstReservationNumber, secondReservationNumber);
  }
}
