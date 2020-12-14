package ca.ulaval.glo4002.reservation.domain.reservation;

import ca.ulaval.glo4002.reservation.domain.Utils.IdentifierGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class ReservationIdentifierGenerator implements IdentifierGenerator {
  private static final AtomicLong                     nextReservationNumber = new AtomicLong(1L);
  private static       ReservationIdentifierGenerator instance;

  private ReservationIdentifierGenerator() {}

  public static ReservationIdentifierGenerator getInstance() {
    // https://www.javacodemonk.com/threadsafe-singleton-design-pattern-java-806ad7e6

    ReservationIdentifierGenerator localInstanceReference = instance;
    if (localInstanceReference == null) {
      synchronized (ReservationIdentifierGenerator.class) {
        localInstanceReference = instance;
        if (localInstanceReference == null) instance = new ReservationIdentifierGenerator();
      }
    }
    return instance;
  }

  @Override public long getNextSequenceNumber() {
    return nextReservationNumber.getAndIncrement();
  }
}
