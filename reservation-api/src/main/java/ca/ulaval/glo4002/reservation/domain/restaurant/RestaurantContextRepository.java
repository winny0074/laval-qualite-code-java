package ca.ulaval.glo4002.reservation.domain.restaurant;

import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;

public interface RestaurantContextRepository {
//  RestaurantConfiguration findByDate(GloDateTime date);
//  RestaurantConfiguration findByName(String name);
  RestaurantConfiguration get();
  void updateEventPeriodDates(GloDateTime eventStart, GloDateTime eventEnd);
  void updateReservationPeriodDates(GloDateTime reservationStart, GloDateTime reservationEnd);

  //  Collection<RestaurantConfiguration> findAll();
}
