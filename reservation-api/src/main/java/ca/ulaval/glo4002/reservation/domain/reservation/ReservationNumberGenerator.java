package ca.ulaval.glo4002.reservation.domain.reservation;

import ca.ulaval.glo4002.reservation.domain.Utils.NumberGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class ReservationNumberGenerator implements NumberGenerator {
  private static final AtomicLong nextReservationNumber = new AtomicLong(1L);
  private static ReservationNumberGenerator instance;

  private ReservationNumberGenerator() {}

  public static ReservationNumberGenerator getInstance() {
    // https://www.javacodemonk.com/threadsafe-singleton-design-pattern-java-806ad7e6

    ReservationNumberGenerator localInstanceReference = instance;
    if (localInstanceReference == null) {
      synchronized (ReservationNumberGenerator.class) {
        localInstanceReference = instance;
        if (localInstanceReference == null) instance = new ReservationNumberGenerator();
      }
    }
    return instance;
  }

  @Override public long getNextSequenceNumber() {
    return nextReservationNumber.getAndIncrement();
  }
}
