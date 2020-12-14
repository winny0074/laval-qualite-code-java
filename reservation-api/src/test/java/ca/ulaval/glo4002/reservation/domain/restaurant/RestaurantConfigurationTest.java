package ca.ulaval.glo4002.reservation.domain.restaurant;

import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantConfigurationTest {

    private final GloDateTime             VALID_EVENT_START_DATE = GloDateTime.ofLocalDateFormat("2150-07-01");
    private final GloDateTime             VALID_EVENT_END_DATE = GloDateTime.ofLocalDateFormat("2150-07-30");
    private final GloDateTime             VALID_RESERVATION_START_DATE = GloDateTime.ofLocalDateFormat("2150-01-01");
    private final GloDateTime             VALID_RESERVATION_END_DATE = GloDateTime.ofLocalDateFormat("2150-07-20");
    private final GloDateTime             VALID_DATE = GloDateTime.ofLocalDateFormat("2150-07-10");
    private final GloDateTime             INVALID_DATE = GloDateTime.ofLocalDateFormat("2150-06-10");
    private final String NAME = "hoppening";
    private int         MAX_PEOPLE_PAR_RESERVATION = 5;
    private int         MAX_DAILY_OCC = 5;
    private       RestaurantConfiguration restaurantConfiguration;

    @BeforeEach
    public void setUp() {
        restaurantConfiguration = new RestaurantConfiguration(NAME, VALID_RESERVATION_START_DATE, VALID_RESERVATION_END_DATE,
                                                              VALID_EVENT_START_DATE, VALID_EVENT_END_DATE, MAX_PEOPLE_PAR_RESERVATION,
                                                              MAX_DAILY_OCC);
    }

    @Test
    public void givenADateEqualToEventStartDate_whenIsOpen_thenReturnTrue() {
        assertTrue(restaurantConfiguration.isOpenOn(VALID_EVENT_START_DATE));
    }

    @Test
    public void givenADateEqualToEventEndDate_whenIsOpen_thenReturnTrue() {
        assertTrue(restaurantConfiguration.isOpenOn(VALID_EVENT_END_DATE));
    }

    @Test
    public void givenADateAfterEventStartDateAndBeforeEventEndDate_whenIsOpen_thenReturnTrue() {
        assertTrue(restaurantConfiguration.isOpenOn(VALID_DATE));
    }

    @Test
    public void givenADateBeforeEventStartDate_whenIsOpen_thenReturnFalse() {
        assertFalse(restaurantConfiguration.isOpenOn(INVALID_DATE));
    }
}
