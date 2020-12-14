package ca.ulaval.glo4002.reservation.infrastructure;

import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.restaurant.RestaurantConfiguration;
import ca.ulaval.glo4002.reservation.domain.restaurant.RestaurantContextRepository;

public class RestaurantContextPersistenceInMemory implements RestaurantContextRepository {
  private static       RestaurantContextPersistenceInMemory instance;
//  private             Collection<RestaurantConfiguration>         periods;
  public static final  String                               HOPPENING                      = "hoppening";
  public static final  String                               EVENT_START_DATE               = "2150-07-20";
  public static final  String                               EVENT_END_DATE                 = "2150-07-30";
  public static final  String                               RESERVATION_START_DATE         = "2150-01-01";
  public static final  String                               RESERVATION_END_DATE           = "2150-07-16";
  private static final int                                  MAXIMUM_PEOPLE_PER_RESERVATION = 6;
  private static final int                                  MAXIMUM_DAILY_OCCUPATION       = 42;
  private              RestaurantConfiguration              restaurantConfiguration;

  private RestaurantContextPersistenceInMemory() {
//    periods = new LinkedList<>();
    restaurantConfiguration =loadData();
  }

  public static RestaurantContextPersistenceInMemory getInstance() {
    // https://www.javacodemonk.com/threadsafe-singleton-design-pattern-java-806ad7e6

    RestaurantContextPersistenceInMemory localInstanceReference = instance;
    if (localInstanceReference == null) {
      synchronized (RestaurantContextPersistenceInMemory.class) {
        localInstanceReference = instance;
        if (localInstanceReference == null) instance = new RestaurantContextPersistenceInMemory();
      }
    }
    return instance;
  }

  private RestaurantConfiguration loadData() {
    GloDateTime startDate = GloDateTime.ofLocalDateFormat(EVENT_START_DATE);
    GloDateTime endDate = GloDateTime.ofLocalDateFormat(EVENT_END_DATE);
    GloDateTime reservationStartDate = GloDateTime.ofLocalDateFormat(RESERVATION_START_DATE);
    GloDateTime reservationEndDate = GloDateTime.ofLocalDateFormat(RESERVATION_END_DATE);
    return new RestaurantConfiguration(HOPPENING, reservationStartDate, reservationEndDate,
                                       startDate, endDate, MAXIMUM_PEOPLE_PER_RESERVATION,
                                       MAXIMUM_DAILY_OCCUPATION);
//    periods.add(hoppening);
  }

  @Override
  public void updateEventPeriodDates(GloDateTime eventStart, GloDateTime eventEnd) {
    restaurantConfiguration.setEventPeriodStartDate(eventStart);
    restaurantConfiguration.setEventPeriodEndDate(eventEnd);
  }

  @Override
  public void updateReservationPeriodDates(GloDateTime reservationStart, GloDateTime reservationEnd) {
    restaurantConfiguration.setReservationPeriodStartDate(reservationStart);
    restaurantConfiguration.setReservationPeriodEndDate(reservationEnd);
  }

//  @Override
//  public RestaurantConfiguration findByDate(GloDateTime date) throws InvalidDinnerDate {
//    return periods.stream()
//                 .filter(event -> date.isSameDayAs(event.getEventPeriodStartDate()) ||
//                                  ( date.isDateAfter(event.getEventPeriodStartDate()) && date.isDateBefore(event.getEventPeriodEndDate()) ) ||
//                                  date.isSameDayAs(event.getEventPeriodEndDate()))
//                 .findFirst()
//                 .orElseThrow(() -> new InvalidDinnerDate(findByName(HOPPENING).getEventPeriodStartDate().toPresentationDateFormat(),
//                                                          findByName(HOPPENING).getEventPeriodEndDate().toPresentationDateFormat()));
//  }

//  @Override
//  public RestaurantConfiguration findByDate(GloDateTime date) throws InvalidDinnerDate {
//    return periods.stream()
//                 .filter(event -> date.isSameDayAs(event.getEventPeriodStartDate()) ||
//                                  ( date.isDateAfter(event.getEventPeriodStartDate()) && date.isDateBefore(event.getEventPeriodEndDate()) ) ||
//                                  date.isSameDayAs(event.getEventPeriodEndDate()))
//                 .findFirst()
//                 .orElseThrow(() -> new InvalidDinnerDate(findByName(HOPPENING).getEventPeriodStartDate().toPresentationDateFormat(),
//                                                          findByName(HOPPENING).getEventPeriodEndDate().toPresentationDateFormat()));
//  }

//  @Override
//  public RestaurantConfiguration findByName(String name) {
//    return periods.stream()
//      .filter(event -> name.toUpperCase().isEquals(event.getName().toUpperCase()))
//      .findFirst()
//      .orElseThrow(() -> new InvalidFormat());
//  }

  @Override
  public RestaurantConfiguration get() {
    return restaurantConfiguration;
  }

//  @Override
//  public Collection<RestaurantConfiguration> findAll() {
//    return periods;
//  }
}
