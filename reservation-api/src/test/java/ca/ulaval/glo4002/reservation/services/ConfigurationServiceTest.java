package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.restaurant.RestaurantConfiguration;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.restaurant.Restaurant;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.configuration.ConfigurationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class ConfigurationServiceTest {
  private final String EARLIER_START_DATE = "2150-03-05";
  private final String EARLIER_END_DATE = "2150-03-13";
  private final String LATER_START_DATE = "2150-03-15";
  private final String LATER_END_DATE = "2150-03-27";

  private ConfigurationService configurationService;
  private ConfigurationDto configurationDto;
  private Restaurant restaurant;
  private RestaurantConfiguration restaurantConfiguration;

  @BeforeEach
  public void setUp() {
    restaurant = mock(Restaurant.class);
    restaurantConfiguration = mock(RestaurantConfiguration.class);
    configurationService = new ConfigurationService(restaurant);
    configurationDto = makeValidConfigurationDto();
  }

  @Test
  public void givenConfiguration_whenUpdatingHoppeningDates_thenCallRestaurantUpdatePeriodDatesForHoppening() {

    when(restaurant.getContext()).thenReturn(restaurantConfiguration);
    when(restaurantConfiguration.getEventPeriodStartDate()).thenReturn(GloDateTime.ofLocalDateFormat(LATER_START_DATE));
    when(restaurantConfiguration.getEventPeriodEndDate()).thenReturn(GloDateTime.ofLocalDateFormat(LATER_END_DATE));
    when(restaurantConfiguration.getReservationPeriodStartDate()).thenReturn(GloDateTime.ofLocalDateFormat(EARLIER_START_DATE));
    when(restaurantConfiguration.getReservationPeriodEndDate()).thenReturn(GloDateTime.ofLocalDateFormat(EARLIER_END_DATE));

    configurationService.updateHoppeningDates(configurationDto);

    verify(restaurant).updatePeriodDates(eq("hoppening"), any(GloDateTime.class), any(GloDateTime.class), any(GloDateTime.class), any(GloDateTime.class));
  }

  private ConfigurationDto makeValidConfigurationDto() {
    ConfigurationDto configuration = new ConfigurationDto();
    configuration.hoppeningStartDate = LATER_START_DATE;
    configuration.hoppeningEndDate = LATER_END_DATE;
    configuration.reservationStartDate = EARLIER_START_DATE;
    configuration.reservationEndDate = EARLIER_END_DATE;

    return configuration;
  }
}
