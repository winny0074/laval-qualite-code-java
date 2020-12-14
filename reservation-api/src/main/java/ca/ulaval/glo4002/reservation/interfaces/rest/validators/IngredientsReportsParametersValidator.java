package ca.ulaval.glo4002.reservation.interfaces.rest.validators;

import ca.ulaval.glo4002.reservation.domain.restaurant.RestaurantContextRepository;
import ca.ulaval.glo4002.reservation.infrastructure.RestaurantContextPersistenceInMemory;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.ingredientsReportsException.InvalidDate;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.ingredientsReportsException.InvalidType;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.common.StringValidator;

public class IngredientsReportsParametersValidator {
  private final String HOPPENING_NAME = "hoppening";
  public IngredientsReportsParametersValidator() {}

  public void validateIngredientsReportsParameters(String startDate, String endDate, String type)
      throws InvalidDate {
    StringValidator.isNullOrEmpty(type, new InvalidType());

    RestaurantContextRepository eventRepo = RestaurantContextPersistenceInMemory.getInstance();
    StringValidator.isNullOrEmpty(startDate, new InvalidDate(eventRepo.get().getEventPeriodStartDate().toPresentationDateFormat(),
                                                             eventRepo.get().getEventPeriodEndDate().toPresentationDateFormat()));
    StringValidator.isNullOrEmpty(endDate, new InvalidDate(eventRepo.get().getEventPeriodStartDate().toPresentationDateFormat(),
                                                           eventRepo.get().getEventPeriodEndDate().toPresentationDateFormat()));
  }
}
