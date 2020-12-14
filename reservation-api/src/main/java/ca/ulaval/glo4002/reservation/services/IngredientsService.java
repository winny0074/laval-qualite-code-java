package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationIdentifierGenerator;
import ca.ulaval.glo4002.reservation.domain.restaurant.Restaurant;
import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.infrastructure.ReservationPersistenceInMemory;
import ca.ulaval.glo4002.reservation.infrastructure.RestaurantContextPersistenceInMemory;
import ca.ulaval.glo4002.reservation.infrastructure.supplier.ExternalIngredientClient;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.OrderInTotalFormatDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.OrderInUnitsFormatDto;
import ca.ulaval.glo4002.reservation.domain.exception.ingredientsReportsException.InvalidDate;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.InvalidFormat;
import ca.ulaval.glo4002.reservation.services.assemblers.OrderInTotalFormatDtoAssembler;
import ca.ulaval.glo4002.reservation.services.assemblers.OrderInUnitFormatDtoAssembler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientsService {
  private final Restaurant restaurant;
  private final OrderInUnitFormatDtoAssembler orderInUnitFormatDtoAssembler;
  private final OrderInTotalFormatDtoAssembler orderInTotalFormatDtoAssembler;

  public IngredientsService() {
    this.orderInTotalFormatDtoAssembler = new OrderInTotalFormatDtoAssembler();
    this.orderInUnitFormatDtoAssembler = new OrderInUnitFormatDtoAssembler();
    this.restaurant = new Restaurant(RestaurantContextPersistenceInMemory.getInstance(),
                                     new ExternalIngredientClient(),
                                     new ReservationPersistenceInMemory(),
                                     new ChefService(),
                                     ReservationIdentifierGenerator.getInstance());
  }

  public IngredientsService(Restaurant restaurant,
                            OrderInTotalFormatDtoAssembler orderInTotalFormatDtoAssembler,
                            OrderInUnitFormatDtoAssembler orderInUnitFormatDtoAssembler) {
    this.restaurant = restaurant;
    this.orderInTotalFormatDtoAssembler = orderInTotalFormatDtoAssembler;
    this.orderInUnitFormatDtoAssembler = orderInUnitFormatDtoAssembler;
  }

  public OrderInUnitsFormatDto getIngredientsToOrderInUnitsFormat(String startDate, String endDate) throws InvalidFormat {
    Map<GloDateTime, IngredientList> ingredientListMapByDate = validateDatesAndMakeIngredientListMap(startDate, endDate);
    return orderInUnitFormatDtoAssembler.from(ingredientListMapByDate);
  }

  public Map<GloDateTime, IngredientList> makeIngredientListMapByDate(List<GloDateTime> dates) {
    Map<GloDateTime, IngredientList> ingredientListsMapByDate = new HashMap<>();
    for (GloDateTime date : dates ) {
      ingredientListsMapByDate.put(date, restaurant.getIngredientsForDate(date));
    }
    return ingredientListsMapByDate;
  }

  public OrderInTotalFormatDto getIngredientsToOrderInTotalFormat(String startDate, String endDate)
      throws InvalidFormat {
    Map<GloDateTime, IngredientList> ingredientListMapByDate = validateDatesAndMakeIngredientListMap(startDate, endDate);
    return orderInTotalFormatDtoAssembler.from(ingredientListMapByDate);
  }

  public Map<GloDateTime, IngredientList> validateDatesAndMakeIngredientListMap(String startDate, String endDate) {
    try {
      GloDateTime firstDate = GloDateTime.ofLocalDateFormat(startDate);
      GloDateTime lastDate = GloDateTime.ofLocalDateFormat(endDate);
      validateIngredientsReportsDates(firstDate, lastDate);
      List<GloDateTime> dates = makeDatesArray(firstDate, lastDate);
      return makeIngredientListMapByDate(dates);
    } catch(InvalidDate error) {
      throw new InvalidDate(this.restaurant.getContext().getEventPeriodStartDate().toPresentationDateFormat(),
                            this.restaurant.getContext().getEventPeriodEndDate().toPresentationDateFormat());
    }
  }

  public List<GloDateTime> makeDatesArray(GloDateTime startDate, GloDateTime endDate)
      throws InvalidFormat {
    if (endDate.isDateBefore(startDate)) {
      throw new InvalidFormat();
    } else {
      return startDate.getInclusiveDateIntervalWith(endDate);
    }
  }

  private void validateIngredientsReportsDates(GloDateTime startDate, GloDateTime endDate) throws InvalidDate {
    if(!restaurant.isOpenOn(startDate) || !restaurant.isOpenOn(endDate)) {
      throw new InvalidDate(this.restaurant.getContext().getEventPeriodStartDate().toPresentationDateFormat(),
                            this.restaurant.getContext().getEventPeriodEndDate().toPresentationDateFormat());
    }
  }
}
