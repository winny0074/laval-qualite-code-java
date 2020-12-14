package ca.ulaval.glo4002.reservation.domain.reservation;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.Utils.IdentifierGenerator;
import ca.ulaval.glo4002.reservation.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.domain.ingredient.meal.MealType;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationTest {
  private final IngredientList      ALL_INGREDIENTS = makeListOfAllIngredientsInRestrictions();
  private       ReservationRequest  basicReservationRequest;
  private       IdentifierGenerator identifierGenerator;
  private       ReservationNumber   identificationNumber;

  @BeforeEach
  public void setUpBasicReservationRequest() {

    basicReservationRequest = ReservationRequestMother.createBasicRequest();
    identifierGenerator = mock(ReservationIdentifierGenerator.class);

    doReturn(1l).when(identifierGenerator).getNextSequenceNumber();
    identificationNumber = ReservationNumber.create(basicReservationRequest.vendorCode, identifierGenerator.getNextSequenceNumber());
  }

  @Test
  public void givenBasicReservationRequest_whenBuildFromIt_thenReturnsReservation() {
    doReturn(1l).when(identifierGenerator).getNextSequenceNumber();

    Reservation reservation = Reservation.from(basicReservationRequest, ALL_INGREDIENTS, identificationNumber);

    assertNotNull(reservation);
  }

  @Test
  public void givenReservationMadeFromRequestForTwoCustomers_whenGetNumberOfCustomers_thenReturnTwo() {
    ReservationRequest reservationRequestForTwo = ReservationRequestMother.createBasicRequestForTwo();

    Reservation reservation = Reservation.from(reservationRequestForTwo, ALL_INGREDIENTS, identificationNumber);

    assertEquals(2, reservation.getNumberOfCustomers());
  }

  @Test
  public void givenReservationWithAllergicCostumer_whenCheckIfReservationContainsAllergicCustomer_thenReturnTrue() {
    ReservationRequest reservationRequestWithAllergies = ReservationRequestMother.createBasicRequestWithAllergies();
    Reservation reservation = Reservation.from(reservationRequestWithAllergies, ALL_INGREDIENTS, identificationNumber);

    boolean containsAllergicCustomer = reservation.containsAllergicCustomer();

    assertTrue(containsAllergicCustomer);
  }

  @Test
  public void givenReservationWithoutAllergicCostumer_whenCheckIfReservationContainsAllergicCustomer_thenReturnFalse() {
    ReservationRequest reservationRequestWithoutAllergies = ReservationRequestMother.createBasicRequestForTwo();
    Reservation reservation = Reservation.from(reservationRequestWithoutAllergies, ALL_INGREDIENTS, identificationNumber);

    boolean containsAllergicCustomer = reservation.containsAllergicCustomer();

    assertFalse(containsAllergicCustomer);
  }

  @Test
  public void givenAnyReservation_whenGetIdentificationNumber_thenGetsNumberFromGenerator() {
    Reservation anyReservation = Reservation.from(basicReservationRequest, ALL_INGREDIENTS, identificationNumber);

    assertNotNull(anyReservation.getIdentificationNumber());

  }

  private IngredientList makeListOfAllIngredientsInRestrictions() {
    List<Ingredient> ingredients = new LinkedList<>();
    for (Restriction restriction : Restriction.values()) {
      for (MealType meal : restriction.getMeals()) {
        ingredients.add(new Ingredient(meal.getName(), new Money(0, 0, ""), 0));
      }
    }
    return new IngredientList(ingredients);
  }
}
