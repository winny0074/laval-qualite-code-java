package ca.ulaval.glo4002.reservation.infrastructure;

import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.ReservationNotFound;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationNumber;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReservationPersistenceInMemory implements ReservationRepository {

  private final HashMap<ReservationNumber, Reservation> reservations;
  private static       ReservationPersistenceInMemory instance;

  public ReservationPersistenceInMemory() {
    this.reservations = new HashMap<>();
  }

  public static ReservationPersistenceInMemory getInstance() {
    // https://www.javacodemonk.com/threadsafe-singleton-design-pattern-java-806ad7e6

    ReservationPersistenceInMemory localInstanceReference = instance;
    if (localInstanceReference == null) {
      synchronized (RestaurantContextPersistenceInMemory.class) {
        localInstanceReference = instance;
        if (localInstanceReference == null) instance = new ReservationPersistenceInMemory();
      }
    }
    return instance;
  }

  public List<Reservation> findAll() {
    return new ArrayList<>(this.reservations.values());
  }

  public Reservation findById(ReservationNumber id) throws ReservationNotFound {
    if (Objects.isNull(reservations) || reservations.size() <= 0) {
      throw new ReservationNotFound(id.getNumber());
    }

    Reservation returnReservation = reservations.get(id);

    if (returnReservation == null) {
      throw new ReservationNotFound(id.getNumber());
    }

    return returnReservation;
  }

  @Override
  public ReservationNumber save(Reservation reservation) {
    reservations.put(reservation.getIdentificationNumber(), reservation);
    return reservation.getIdentificationNumber();
  }

  public void remove(Reservation reservation) {
    reservations.remove(reservation.getIdentificationNumber());
  }

  @Override
  public void clear() {
    reservations.clear();
  }

  public List<Reservation> findByDate(GloDateTime date) {
    return findAll().stream()
            .filter(reservation -> date.isSameDayAs(reservation.getDinnerDate()))
            .collect(Collectors.toList());
  }

  public long getDailyOccupancy(GloDateTime dayToGetOccupancyOf) {
    List<Reservation> reservations = findAll();
    return reservations.stream()
            .filter(reservation -> dayToGetOccupancyOf.isSameDayAs(reservation.getDinnerDate()))
            .mapToLong(Reservation::getNumberOfCustomers)
            .sum();
  }
}
