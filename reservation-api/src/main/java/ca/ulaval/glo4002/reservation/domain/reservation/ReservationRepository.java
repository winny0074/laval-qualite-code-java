package ca.ulaval.glo4002.reservation.domain.reservation;

import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.ReservationNotFound;

import java.util.List;

public interface ReservationRepository {
  void clear();

  List<Reservation> findByDate(GloDateTime date);

  List<Reservation> findAll();

  Reservation findById(ReservationNumber id) throws ReservationNotFound;

  ReservationNumber save(Reservation reservation);

  void remove(Reservation reservation);

  long getDailyOccupancy(GloDateTime day);
}
