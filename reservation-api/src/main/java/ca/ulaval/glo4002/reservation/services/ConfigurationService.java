package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.exception.ingredientsReportsException.InvalidDate;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationIdentifierGenerator;
import ca.ulaval.glo4002.reservation.domain.restaurant.Restaurant;
import ca.ulaval.glo4002.reservation.infrastructure.ReservationPersistenceInMemory;
import ca.ulaval.glo4002.reservation.infrastructure.RestaurantContextPersistenceInMemory;
import ca.ulaval.glo4002.reservation.infrastructure.supplier.ExternalIngredientClient;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.configuration.ConfigurationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.configurationException.InvalidPeriodDate;

public class ConfigurationService {
  private final String HOPPENING_NAME = "hoppening";
  private final String USE_OLD_DATE_INDICATOR = "OLD_DATE";
  private final Restaurant restaurant;

  public ConfigurationService() {
    this.restaurant = new Restaurant(RestaurantContextPersistenceInMemory.getInstance(),
                                     new ExternalIngredientClient(),
                                     new ReservationPersistenceInMemory(),
                                     new ChefService(),
                                     ReservationIdentifierGenerator.getInstance());
  }

  public ConfigurationService(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  public void updateHoppeningDates(ConfigurationDto configuration) {
    try {
      GloDateTime eventStartDate = this.restaurant.getContext().getEventPeriodStartDate();
      GloDateTime eventEndDate = this.restaurant.getContext().getEventPeriodEndDate();
      GloDateTime reservationStartDate = this.restaurant.getContext().getReservationPeriodStartDate();
      GloDateTime reservationEndDate = this.restaurant.getContext().getReservationPeriodEndDate();

      if(!configuration.hoppeningStartDate.equals(USE_OLD_DATE_INDICATOR)) {
        eventStartDate = GloDateTime.ofLocalDateFormat(configuration.hoppeningStartDate);
        eventEndDate = GloDateTime.ofLocalDateFormat(configuration.hoppeningEndDate);
      }

      if(!configuration.reservationStartDate.equals(USE_OLD_DATE_INDICATOR)) {
        reservationStartDate = GloDateTime.ofLocalDateFormat(configuration.reservationStartDate);
        reservationEndDate = GloDateTime.ofLocalDateFormat(configuration.reservationEndDate);
      }

      restaurant.updatePeriodDates(HOPPENING_NAME, eventStartDate, eventEndDate,
                                   reservationStartDate, reservationEndDate);
    }
    catch(InvalidDate error) {
      throw new InvalidPeriodDate();
    }
  }
}
