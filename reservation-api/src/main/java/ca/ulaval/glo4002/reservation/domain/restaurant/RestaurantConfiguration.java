package ca.ulaval.glo4002.reservation.domain.restaurant;

import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;

public class RestaurantConfiguration {

  private String      name;
  private GloDateTime eventPeriodStartDate;
  private GloDateTime eventPeriodEndDate;
  private GloDateTime reservationPeriodStartDate;
  private GloDateTime reservationPeriodEndDate;
  private int         maximumPeopleParReservation;
  private int         maximumDailyOccupation;

  public RestaurantConfiguration(String name, GloDateTime reservationStart, GloDateTime reservationEnd,
                                 GloDateTime eventStart, GloDateTime eventEnd, int maximumPeopleParReservation,
                                 int maximumDailyOccupation) {
    this.name = name;
    this.eventPeriodStartDate = eventStart;
    this.eventPeriodEndDate = eventEnd;
    this.reservationPeriodStartDate = reservationStart;
    this.reservationPeriodEndDate = reservationEnd;
    this.maximumPeopleParReservation = maximumPeopleParReservation;
    this.maximumDailyOccupation = maximumDailyOccupation;
  }

  public String getName() {
    return name;
  }

  public GloDateTime getEventPeriodStartDate() {
    return eventPeriodStartDate;
  }

  public void setEventPeriodStartDate(GloDateTime startDate) {
    this.eventPeriodStartDate = startDate;
  }

  public GloDateTime getEventPeriodEndDate() {
    return eventPeriodEndDate;
  }

  public void setEventPeriodEndDate(GloDateTime endDate) {
    this.eventPeriodEndDate = endDate;
  }

  public GloDateTime getReservationPeriodStartDate() {
    return reservationPeriodStartDate;
  }

  public void setReservationPeriodStartDate(GloDateTime reservationPeriodStartDate) {
    this.reservationPeriodStartDate = reservationPeriodStartDate;
  }

  public GloDateTime getReservationPeriodEndDate() {
    return reservationPeriodEndDate;
  }

  public void setReservationPeriodEndDate(GloDateTime reservationPeriodEndDate) {
    this.reservationPeriodEndDate = reservationPeriodEndDate;
  }

  public int getMaximumPeopleParReservation() {
    return maximumPeopleParReservation;
  }

  public int getMaximumDailyOccupation() {
    return maximumDailyOccupation;
  }

  public boolean isOpenOn(GloDateTime date) {
    return (date.isSameDayAs(eventPeriodStartDate) ||
            (date.isDateAfter(eventPeriodStartDate) && date.isDateBefore(eventPeriodEndDate)) ||
            date.isSameDayAs(eventPeriodEndDate));
  }

//  public void updateEventPeriodDates(GloDateTime eventStart, GloDateTime eventEnd) {
//    eventPeriodStartDate = eventStart;
//    eventPeriodEndDate = eventEnd;
//  }
//
//  public void updateReservationPeriodDates(GloDateTime reservationStart, GloDateTime reservationEnd) {
//    reservationPeriodStartDate = reservationStart;
//    reservationPeriodEndDate = reservationEnd;
//  }

//  public Long reserve(ReservationRequest request, IngredientList availableIngredients) {
//    Reservation reservation = Reservation.from(request, availableIngredients, reservationNumberGenerator);
//    validateSocialDistancing(reservation);
//    validateReservationDate(reservation.getReservationDate());
//    validateAllergies(reservation);
//    reservationRepository.save(reservation);
//    chefService.save(reservation.getDinnerDate(), reservation.getAllRestrictions()) ;
//    return reservation.getIdentificationNumber();
//  }
//
//  public Reservation findReservationById(long id) {
//    return reservationRepository.findById(id);
//  }
//
//  private void validateSocialDistancing(Reservation reservation) {
//    validatePeoplePerReservation(reservation.getCustomers());
//    validatePeoplePerDay(reservation.getCustomers(), reservation.getDinnerDate());
//  }
//
//  private void validatePeoplePerReservation(Collection<Customer> customersInReservation) {
//    if (customersInReservation.size() > MAXIMUM_PEOPLE_PER_RESERVATION)
//      throw new InvalidPeopleAmount();
//  }
//
//  private void validatePeoplePerDay(Collection<Customer> customersInReservation, GloDateTime dinnerDate) {
//    int totalCustomersInReservation = customersInReservation.size();
//    long customersInRestaurantOnThatDay =
//        reservationRepository.getDailyOccupancy(dinnerDate);
//    if (totalCustomersInReservation + customersInRestaurantOnThatDay > MAXIMUM_DAILY_OCCUPATION)
//      throw new InvalidPeopleAmount();
//  }
//
//  private void validateReservationDate(GloDateTime reservationDate) throws InvalidReservationDate {
//    if (reservationDate.isDateBefore(getReservationPeriodStartDate())
//        || reservationDate.isDateAfter(getReservationPeriodEndDate())) {
//      throw new InvalidReservationDate(reservationPeriodStartDate.toPresentationDateFormat(), reservationPeriodEndDate.toPresentationDateFormat());
//    }
//  }
//
//  private void validateAllergies(Reservation reservation) {
//    IngredientList ingredientsOnDinnerDate = getIngredientsForDate(reservation.getDinnerDate());
//    if (reservation.containsAllergicCustomer() && ingredientsOnDinnerDate.containsAllergen())
//      throw new AllergyException();
//
//    Collection<Customer> customersOnDinnerDate = getAllCustomersForDate(reservation.getDinnerDate());
//    if (customersOnDinnerDate.stream().anyMatch(Customer::hasAllergy) && reservation.containsAllergen())
//      throw new AllergyException();
//  }
//
//  private Collection<Customer> getAllCustomersForDate(GloDateTime dinnerDate) {
//    return reservationRepository.findByDate(dinnerDate).stream()
//                                                       .flatMap(reservation -> reservation.getCustomers().stream())
//                                                       .collect(Collectors.toList());
//  }
//
//  public IngredientList getIngredientsForDate(GloDateTime date) {
//    return new IngredientList(reservationRepository.findByDate(date)
//                                                   .stream()
//                                                   .flatMap(reservation -> reservation.getIngredients().toList().stream())
//                                                   .collect(Collectors.toList()));
//  }
//
//  // Ne pas retirer - Est utilisé implicitement pour certains tests
//  public Collection<Reservation> findAll() {
//    return reservationRepository.findAll();
//  }
//
//  // Ne pas retirer - Est utilisé implicitement pour certains tests
//  public void remove(Reservation reservation) {
//    reservationRepository.remove(reservation);
//  }
}
