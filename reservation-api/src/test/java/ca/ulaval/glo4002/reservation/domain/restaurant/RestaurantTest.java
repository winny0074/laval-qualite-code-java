package ca.ulaval.glo4002.reservation.domain.restaurant;

import ca.ulaval.glo4002.reservation.domain.Utils.IdentifierGenerator;
import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.exception.configurationException.InvalidTimeFrame;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.ReservationNotFound;
import ca.ulaval.glo4002.reservation.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientRepository;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import ca.ulaval.glo4002.reservation.domain.reservation.*;
import ca.ulaval.glo4002.reservation.services.ChefService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

import java.util.List;

public class RestaurantTest {
  private final GloDateTime VALID_DINNER_DATE = GloDateTime.ofLocalDateFormat("2150-07-20");
  private final GloDateTime INVALID_DINNER_DATE = GloDateTime.ofLocalDateFormat("2150-09-20");
  private final Ingredient LIMITED_INGREDIENT = new Ingredient("tomato", new Money(0, ""), 0);
  private final String PERIOD_NAME = "period";
  private final ReservationNumber A_RESERVATION_ID = ReservationNumber.create("TEAM-123");
  private final int NUMBER_OF_DAY_SINCE_EVENT_STARTED = 5;

  private Restaurant                  restaurant;
  private Restaurant                  restaurantSpy;
  private Reservation                 reservation;
  private RestaurantContextRepository restaurantContextRepository;
  private RestaurantConfiguration     restaurantConfiguration;
  private IngredientRepository        ingredientRepository;
  private IngredientList              ingredientList;
  private ReservationRequest          basicReservationRequest;
  private ReservationRepository       reservationRepository;
  private ChefService                 chefService;
  public  IdentifierGenerator         identifierGenerator;

  @BeforeEach
  public void setUpBasicReservationRequest() {
    basicReservationRequest = ReservationRequestMother.createBasicRequest();
    basicReservationRequest.dinnerDate = VALID_DINNER_DATE;
  }

  @BeforeEach
  public void setUp() {
    restaurantContextRepository = mock(RestaurantContextRepository.class);
    restaurantConfiguration = mock(RestaurantConfiguration.class);
    ingredientRepository = mock(IngredientRepository.class);
    reservationRepository = mock(ReservationRepository.class);
    chefService = mock(ChefService.class);
    identifierGenerator = mock(ReservationIdentifierGenerator.class);
    ingredientList = new IngredientList();
    reservation = mock(Reservation.class);

//    when(restaurantContextRepository.findByDate(VALID_DINNER_DATE)).thenReturn(restaurantConfiguration);
//    when(restaurantContextRepository.findByDate(INVALID_DINNER_DATE)).thenThrow(new InvalidDinnerDate("", ""));
//    when(restaurantContextRepository.findByName(PERIOD_NAME)).thenReturn(restaurantConfiguration);
//    when(restaurantContextRepository.findAll()).thenReturn(List.of(restaurantConfiguration));
    when(restaurantContextRepository.get()).thenReturn(restaurantConfiguration);
    when(restaurantConfiguration.getEventPeriodStartDate()).thenReturn(VALID_DINNER_DATE);
    when(reservation.getReservationDate()).thenReturn(VALID_DINNER_DATE);
    when(reservation.getIdentificationNumber()).thenReturn(A_RESERVATION_ID);
//    when(restaurantConfiguration.getIngredientsForDate(VALID_DINNER_DATE)).thenReturn(ingredientList);
    when(ingredientRepository.findAll()).thenReturn(ingredientList);

    restaurant = new Restaurant(restaurantContextRepository, ingredientRepository,
                                reservationRepository, chefService, identifierGenerator);
    restaurantSpy = spy(restaurant);
  }

  @Test
  public void givenAValidReservationRequest_whenReserve_thenCallsIngredientRepositoryFindAll() {

    doReserve(() -> verify(ingredientRepository).findAll());
  }

  @Test
  public void givenAValidReservationRequest_whenReserve_thenCallReservationSaveMethod() {

    doReserve(() -> verify(reservationRepository).save(reservation));
  }

  @Test
  public void givenAValidReservationRequest_whenReserve_thenCallChefSaveMethod() {

    doReserve(() -> verify(chefService).save(reservation.getDinnerDate(), reservation.getAllRestrictions()));
  }

  @Test
  public void givenAListWithLimitedIngredient_whenFilterOutLimitedIngredients_thenRemoveLimitedIngredientFromList() {
    ingredientList.add(LIMITED_INGREDIENT);

    restaurant.filterOutLimitedIngredients(ingredientList, NUMBER_OF_DAY_SINCE_EVENT_STARTED);

    assertEquals(0, ingredientList.toList().size());
  }

  @Test
  public void givenRepositoryContainsId_whenLookingForReservationWithId_thenReturnReservation() {
    when(reservationRepository.findById(A_RESERVATION_ID)).thenReturn(reservation);

    Reservation returnedReservation = restaurant.findReservationById(A_RESERVATION_ID.getNumber());

    assertEquals(reservation, returnedReservation);
  }

  @Test
  public void givenRepositoryDoesNotContainId_whenLookingForReservationWithId_thenThrowReservationNotFoundException() {
    when(reservationRepository.findById(A_RESERVATION_ID)).thenThrow(new ReservationNotFound(A_RESERVATION_ID.getNumber()));

    Executable findReservation = () -> restaurant.findReservationById(A_RESERVATION_ID.getNumber());

    assertThrows(ReservationNotFound.class, findReservation);
  }

  @Test
  public void whenLookingForIngredientsWithValidDate_thenReturnsTheAssociatedIngredientList() {
    List<Reservation> reservations = List.of(this.reservation);
    ingredientList.add(LIMITED_INGREDIENT);

    when(reservationRepository.findByDate(VALID_DINNER_DATE)).thenReturn(reservations);
    when(reservation.getIngredients()).thenReturn(ingredientList);

    IngredientList returnedIngredientList = restaurant.getIngredientsForDate(VALID_DINNER_DATE);

    assertTrue(ingredientList.isEquals(returnedIngredientList));
  }

  @Test
  public void givenValidDates_whenUpdatingPeriodDates_thenCallsPeriodUpdateDates() {
    GloDateTime reservationStartDate = VALID_DINNER_DATE;
    GloDateTime reservationEndDate = VALID_DINNER_DATE.plusDays(1);
    GloDateTime eventStartDate = VALID_DINNER_DATE.plusDays(2);
    GloDateTime eventEndDate = VALID_DINNER_DATE.plusDays(3);

    restaurant.updatePeriodDates(PERIOD_NAME, eventStartDate, eventEndDate, reservationStartDate, reservationEndDate);

    verify(restaurantContextRepository).updateEventPeriodDates(eventStartDate, eventEndDate);
    verify(restaurantContextRepository).updateReservationPeriodDates(reservationStartDate, reservationEndDate);
  }

  @Test
  public void givenInvalidDates_whenUpdatingPeriodDates_thenThrowInvalidTimeFrameException() {
    GloDateTime reservationStartDate = VALID_DINNER_DATE;
    GloDateTime reservationEndDate = VALID_DINNER_DATE.plusDays(1);
    GloDateTime eventStartDate = VALID_DINNER_DATE.plusDays(2);
    GloDateTime eventEndDate = VALID_DINNER_DATE.plusDays(3);

    Executable updateDates = () ->  restaurant.updatePeriodDates(PERIOD_NAME, reservationStartDate, reservationEndDate, eventStartDate, eventEndDate);

    assertThrows(InvalidTimeFrame.class, updateDates);
  }

  @Test
  public void givenAValidDate_whenCheckingIfRestaurantIsOpen_thenReturnTrue() {
    when(restaurantConfiguration.isOpenOn(VALID_DINNER_DATE)).thenReturn(true);
    assertTrue(restaurant.isOpenOn(VALID_DINNER_DATE));
  }

  @Test
  public void givenAInvalidDate_whenCheckingIfRestaurantIsOpen_thenReturnFalse() {
    when(restaurantConfiguration.isOpenOn(VALID_DINNER_DATE)).thenReturn(false);
    assertFalse(restaurant.isOpenOn(INVALID_DINNER_DATE));
  }

  private void doNothingWhileValidating() {
    doNothing().when(restaurantSpy).validateSocialDistancing(reservation);
    doNothing().when(restaurantSpy).validateReservationDate(any());
    doNothing().when(restaurantSpy).validateAllergies(reservation);
  }

  public void doReserve(Runnable runnable) {

    try (MockedStatic<Reservation> ReservationMock = mockStatic(Reservation.class)) {
      try (MockedStatic<ReservationNumber> reservationNumberMockedStatic = mockStatic(ReservationNumber.class)) {
        restaurantSpy = spy(restaurant);

        doNothingWhileValidating();

        long nextSequenceNumber = identifierGenerator.getNextSequenceNumber();

        reservationNumberMockedStatic.when(() -> ReservationNumber.create(basicReservationRequest.vendorCode, nextSequenceNumber))
                                     .thenReturn(A_RESERVATION_ID);

        ReservationMock.when(() -> Reservation.from(basicReservationRequest, ingredientList, A_RESERVATION_ID))
                       .thenReturn(reservation);

        restaurantSpy.reserve(basicReservationRequest);

      }
    }

    runnable.run();
  }

}
