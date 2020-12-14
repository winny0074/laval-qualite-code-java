package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.exception.ingredientsReportsException.InvalidDate;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.domain.restaurant.Restaurant;
import ca.ulaval.glo4002.reservation.domain.restaurant.RestaurantConfiguration;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.OrderInTotalFormatDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.OrderInUnitsFormatDto;
import ca.ulaval.glo4002.reservation.services.assemblers.OrderInTotalFormatDtoAssembler;
import ca.ulaval.glo4002.reservation.services.assemblers.OrderInUnitFormatDtoAssembler;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IngredientsServiceTest {

  private final        String                         INVALID_DATE = "";
  private final        String                         VALID_DATE = "2050-06-20";
  private static final String                         AN_EARLIER_VALID_DATE = "2050-06-20";
  private static final String                         A_LATER_VALID_DATE = "2050-06-21";
  private              IngredientsService             ingredientsService;
  private IngredientsService             ingredientsServiceSpy;
  private Restaurant                     restaurant;
  private OrderInTotalFormatDtoAssembler orderInTotalFormatDtoAssembler;
  private OrderInUnitFormatDtoAssembler  orderInUnitFormatDtoAssembler;
  private IngredientList                 ingredientList;
  private OrderInUnitsFormatDto          orderInUnitsFormatDto;
  private OrderInTotalFormatDto          orderInTotalFormatDto;
  private RestaurantConfiguration        restaurantConfiguration;

    @BeforeEach
    public void setUp() {
        restaurant = mock(Restaurant.class);
        ingredientList = mock(IngredientList.class);
        orderInUnitsFormatDto = mock(OrderInUnitsFormatDto.class);
        orderInTotalFormatDto = mock(OrderInTotalFormatDto.class);
        orderInTotalFormatDtoAssembler = mock(OrderInTotalFormatDtoAssembler.class);
        orderInUnitFormatDtoAssembler = mock(OrderInUnitFormatDtoAssembler.class);
        ingredientsService = new IngredientsService(restaurant, orderInTotalFormatDtoAssembler, orderInUnitFormatDtoAssembler);
        ingredientsServiceSpy = spy(ingredientsService);
        restaurantConfiguration = mock(RestaurantConfiguration.class);

        doReturn(restaurantConfiguration).when(restaurant).getContext();
        doReturn(GloDateTime.ofLocalDateFormat(VALID_DATE)).when(restaurantConfiguration).getEventPeriodStartDate();
        doReturn(GloDateTime.ofLocalDateFormat(A_LATER_VALID_DATE)).when(restaurantConfiguration).getEventPeriodEndDate();
    }


  @Test
  public void givenAnInvalidDate_whenGetIngredientsOrderIsInUnitFormat_thenThrowInvalidDate() {

    Executable getIngredients = () -> ingredientsService.getIngredientsToOrderInUnitsFormat(VALID_DATE, INVALID_DATE);

    assertThrows(InvalidDate.class, getIngredients);
  }

  @Test
  public void givenAStartDateAndEndDate_whenGetIngredientsOrderIsInUnitFormat_thenReturnAnOrderInUnitFormat() {
      Map<GloDateTime, IngredientList> ingredientListMap = Map.of(GloDateTime.ofLocalDateFormat(VALID_DATE), ingredientList);
      doReturn(ingredientListMap).when(ingredientsServiceSpy).validateDatesAndMakeIngredientListMap(VALID_DATE, A_LATER_VALID_DATE);
      doReturn(orderInUnitsFormatDto).when(orderInUnitFormatDtoAssembler).from(ingredientListMap);


      OrderInUnitsFormatDto result = ingredientsServiceSpy.getIngredientsToOrderInUnitsFormat(VALID_DATE, A_LATER_VALID_DATE);
      assertSame(orderInUnitsFormatDto, result);
      assertTrue(orderInUnitsFormatDto instanceof OrderInUnitsFormatDto);
  }

  @Test
  public void givenAnInvalidDate_whenGetIngredientsOrderIsInTotalFormat_thenThrowInvalidDate() {
    Executable getIngredients = () -> ingredientsService.getIngredientsToOrderInTotalFormat(VALID_DATE, INVALID_DATE);

    assertThrows(InvalidDate.class, getIngredients);
  }

    @Test
    public void givenAStartDateAndEndDate_whenGetIngredientsOrderIsInTotalFormat_thenReturnAnOrderInTotalFormat() {
        Map<GloDateTime, IngredientList> ingredientListMap = Map.of(GloDateTime.ofLocalDateFormat(VALID_DATE), ingredientList);
        doReturn(ingredientListMap).when(ingredientsServiceSpy).validateDatesAndMakeIngredientListMap(VALID_DATE, A_LATER_VALID_DATE);
        doReturn(orderInTotalFormatDto).when(orderInTotalFormatDtoAssembler).from(ingredientListMap);


        OrderInTotalFormatDto result = ingredientsServiceSpy.getIngredientsToOrderInTotalFormat(VALID_DATE, A_LATER_VALID_DATE);

        assertSame(orderInTotalFormatDto, result);
        assertTrue(orderInTotalFormatDto instanceof OrderInTotalFormatDto);
    }

  @Test
  public void givenIncorrectlyOrderedDates_whenGetIngredientsOrderIsInUnitFormat_thenThrowInvalidDate() {
    doReturn(false).when(restaurant).isOpenOn(any());
    Executable getIngredients = () -> ingredientsService.getIngredientsToOrderInUnitsFormat(A_LATER_VALID_DATE, AN_EARLIER_VALID_DATE);

    assertThrows(InvalidDate.class, getIngredients);
  }

  @Test
  public void givenDateListContainingADateWhichAlreadyHaveAnEntryInTheRepo_whenMakeIngredientListMapByDate_thenGroupIngredientListUnderTheGivenDate() {
      GloDateTime dateWithEntryInRepo = GloDateTime.ofLocalDateFormat(VALID_DATE);
      List<GloDateTime> dates = List.of(dateWithEntryInRepo, GloDateTime.ofLocalDateFormat(A_LATER_VALID_DATE));

      doReturn(ingredientList).when(restaurant).getIngredientsForDate(dateWithEntryInRepo);

      Map<GloDateTime, IngredientList> ingredientListMap = ingredientsService.makeIngredientListMapByDate(dates);

      assertTrue(ingredientListMap.get(dateWithEntryInRepo) != null);
  }

  @Test
  public void givenAValidStartDateAndAValidEndDate_whenMakeDatesArray_thenReturnAnInclusiveDateInterval() {

      List<GloDateTime> gloDateTimes = ingredientsService.makeDatesArray(GloDateTime.ofLocalDateFormat(VALID_DATE),
                                                                         GloDateTime.ofLocalDateFormat(A_LATER_VALID_DATE));

      List<GloDateTime> expectedResult = GloDateTime.ofLocalDateFormat(VALID_DATE)
                                                    .getInclusiveDateIntervalWith(GloDateTime.ofLocalDateFormat(A_LATER_VALID_DATE));

      assertArrayEquals(gloDateTimes.stream().map(GloDateTime::toLocalDateFormat).collect(Collectors.toList()).toArray(),
                        expectedResult.stream().map(GloDateTime::toLocalDateFormat).collect(Collectors.toList()).toArray());

  }

  @Test
  public void givenIncorrectlyOrderedDates_whenGetIngredientsOrderIsInTotalFormat_thenThrowInvalidDate() {
    Executable getIngredients = () -> ingredientsService.getIngredientsToOrderInTotalFormat(A_LATER_VALID_DATE, AN_EARLIER_VALID_DATE);

    assertThrows(InvalidDate.class, getIngredients);
  }
}
