package ca.ulaval.glo4002.reservation.domain.restaurant;

import ca.ulaval.glo4002.reservation.domain.Utils.IdentifierGenerator;
import ca.ulaval.glo4002.reservation.domain.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.exception.configurationException.InvalidTimeFrame;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.*;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientRepository;
import ca.ulaval.glo4002.reservation.domain.ingredient.LimitedIngredient;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationNumber;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationRequest;
import ca.ulaval.glo4002.reservation.services.ChefService;

import java.util.Collection;
import java.util.stream.Collectors;

public class Restaurant {
  private RestaurantContextRepository restaurantContextRepository;
  private IngredientRepository        ingredientRepository;
  private ReservationRepository       reservationRepository;
  private ChefService                 chefService;
  private IdentifierGenerator         identifierGenerator;

  private RestaurantConfiguration context;

  public Restaurant(RestaurantContextRepository restaurantContextRepository,
                    IngredientRepository ingredientRepository, ReservationRepository reservationRepository,
                    ChefService chefService, IdentifierGenerator identifierGenerator) {
    this.restaurantContextRepository = restaurantContextRepository;
    this.ingredientRepository = ingredientRepository;
    this.reservationRepository = reservationRepository;
    this.chefService = chefService;
    this.identifierGenerator = identifierGenerator;
    this.context = restaurantContextRepository.get();
  }

  public Restaurant(RestaurantContextRepository restaurantContextRepository) {
    this.restaurantContextRepository = restaurantContextRepository;
    this.context = restaurantContextRepository.get();
  }

  public String reserve(ReservationRequest reservationRequest) throws InvalidDinnerDate {
//    RestaurantConfiguration event = restaurantContextRepository.get();
    IngredientList availableIngredients = ingredientRepository.findAll();
    filterOutLimitedIngredients(availableIngredients, this.context.getEventPeriodStartDate().countDaysFrom(reservationRequest.dinnerDate));

    ReservationNumber reservationNumber = ReservationNumber.create(reservationRequest.vendorCode, identifierGenerator.getNextSequenceNumber());
    Reservation reservation = Reservation.from(reservationRequest, availableIngredients, reservationNumber);

    validateSocialDistancing(reservation);
    validateReservationDate(reservation.getReservationDate());
    validateAllergies(reservation);
    reservationRepository.save(reservation);
    chefService.save(reservation.getDinnerDate(), reservation.getAllRestrictions()) ;

//    return event.reserve(reservationRequest, availableIngredients);
    return reservation.getIdentificationNumber().getNumber();
  }

  public void filterOutLimitedIngredients(IngredientList availableIngredients, int daysSinceEventStart) {
    for(LimitedIngredient limitedIngredient : LimitedIngredient.values()) {
      if (limitedIngredient.getUnavailableDurationInDays() >= daysSinceEventStart) {
        availableIngredients.removeByName(limitedIngredient.getName());
      }
    }
  }

  public Reservation findReservationById(String id) {
    try {
      ReservationNumber identificationNumber = ReservationNumber.create(id);
      return reservationRepository.findById(identificationNumber);
    } catch (ReservationNotFound e) {
      throw new ReservationNotFound(id);
    }

//    for (RestaurantConfiguration event : restaurantContextRepository.findAll()) {
//      try {
//        return reservationRepository.findById(id);
//      } catch (ReservationNotFound e) {}
//    }
//    throw new ReservationNotFound(id);
  }

//  public IngredientList getIngredientsForDate(GloDateTime date) {
//    RestaurantConfiguration event = restaurantContextRepository.findByDate(date);
//    return event.getIngredientsForDate(date);
//  }

  public void updatePeriodDates(String eventName, GloDateTime eventStartDate, GloDateTime eventEndDate, GloDateTime reservationPeriodStartDate, GloDateTime reservationPeriodEndDate) {
    if(eventStartDate.isDateAfter(reservationPeriodEndDate) && eventEndDate.isDateAfter(eventStartDate)
            && reservationPeriodEndDate.isDateAfter(reservationPeriodStartDate)) {

      //RestaurantConfiguration eventToUpdate = restaurantContextRepository.get();

      restaurantContextRepository.updateEventPeriodDates(eventStartDate, eventEndDate);
      restaurantContextRepository.updateReservationPeriodDates(reservationPeriodStartDate, reservationPeriodEndDate);
    } else {
      throw new InvalidTimeFrame();
    }
  }

  public boolean isOpenOn(GloDateTime date) {
    return this.context.isOpenOn(date);
  }

  public void validateSocialDistancing(Reservation reservation) {
    validatePeoplePerReservation(reservation.getCustomers());
    validatePeoplePerDay(reservation.getCustomers(), reservation.getDinnerDate());
  }

  public void validatePeoplePerReservation(Collection<Customer> customersInReservation) {
    if (customersInReservation.size() > restaurantContextRepository.get().getMaximumPeopleParReservation())
      throw new InvalidPeopleAmount();
  }

  public void validatePeoplePerDay(Collection<Customer> customersInReservation, GloDateTime dinnerDate) {
    int totalCustomersInReservation = customersInReservation.size();
    long customersInRestaurantOnThatDay =
            reservationRepository.getDailyOccupancy(dinnerDate);
    if (totalCustomersInReservation + customersInRestaurantOnThatDay > this.context.getMaximumDailyOccupation())
      throw new InvalidPeopleAmount();
  }

  public void validateReservationDate(GloDateTime reservationDate) throws InvalidReservationDate {
    if (reservationDate.isDateBefore(this.context.getReservationPeriodStartDate())
            || reservationDate.isDateAfter(this.context.getReservationPeriodEndDate())) {
      throw new InvalidReservationDate(this.context.getReservationPeriodStartDate().toPresentationDateFormat(), this.context.getReservationPeriodEndDate().toPresentationDateFormat());
    }
  }

  public void validateAllergies(Reservation reservation) {
    IngredientList ingredientsOnDinnerDate = getIngredientsForDate(reservation.getDinnerDate());
    if (reservation.containsAllergicCustomer() && ingredientsOnDinnerDate.containsAllergen())
      throw new AllergyException();

    Collection<Customer> customersOnDinnerDate = getAllCustomersForDate(reservation.getDinnerDate());
    if (customersOnDinnerDate.stream().anyMatch(Customer::hasAllergy) && reservation.containsAllergen())
      throw new AllergyException();
  }

  public Collection<Customer> getAllCustomersForDate(GloDateTime dinnerDate) {
    return reservationRepository.findByDate(dinnerDate).stream()
                                .flatMap(reservation -> reservation.getCustomers().stream())
                                .collect(Collectors.toList());
  }

  public IngredientList getIngredientsForDate(GloDateTime date) {
    return new IngredientList(reservationRepository.findByDate(date)
                                                   .stream()
                                                   .flatMap(reservation -> reservation.getIngredients().toList().stream())
                                                   .collect(Collectors.toList()));
  }

  public RestaurantConfiguration getContext() {
    return context;
  }

  // Ne pas retirer - Est utilisé implicitement pour certains tests
  public Collection<Reservation> findAllReservations() {
    return reservationRepository.findAll();
  }

  // Ne pas retirer - Est utilisé implicitement pour certains tests
  public void remove(Reservation reservation) {
    reservationRepository.remove(reservation);
  }
}
