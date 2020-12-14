package ca.ulaval.glo4002.reservation.domain.customer;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.IngredientNotAvailable;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
  private static final String ANY_NAME = "John";
  private static final List<Restriction> ANY_RESTRICTION_LIST = new LinkedList<>();
  private static final IngredientList INGREDIENT_LIST_MISSING_REQUIRED_INGREDIENT = new IngredientList();
  private Customer customerWithRestrictions;
  private Customer customerWithoutRestrictions;

  @BeforeEach
  public void setup() {
    customerWithoutRestrictions = CustomerMother.createCustomerWithoutRestrictions();
    customerWithRestrictions = CustomerMother.createCustomerWithRestrictions();
  }

  @Test
  public void givenNoRestriction_whenGetPrice_shouldReturnBasePrice() {
    Money price = customerWithoutRestrictions.getPrice();
    Money basePrice = new Money(Restriction.getBasePrice());

    assertTrue(price.isEqual(basePrice));
  }

  @Test
  public void givenRestrictions_whenGetPrice_shouldReturnBasePricePlusRestrictionsPrices() {
    Money totalPrice = customerWithRestrictions.getPrice();

    Money basePrice = new Money(Restriction.getBasePrice());
    Money expectedPrice = customerWithRestrictions.getRestrictions()
                                                  .stream()
                                                  .map(Restriction::getPrice)
                                                  .reduce(basePrice, Money::add);
    assertTrue(totalPrice.isEqual(expectedPrice));
  }

  @Test
  public void givenAllergyRestriction_whenCheckIfHasAllergy_thenReturnTrue() {
    Customer customerWithAllergy = CustomerMother.createCustomerWithAllergies();

    boolean hasAllergy = customerWithAllergy.hasAllergy();

    assertTrue(hasAllergy);
  }

  @Test
  public void givenAllRestrictionsExceptAllergy_whenCheckIfHasAllergy_thenReturnFalse() {
    Customer customerWithoutAllergy = CustomerMother.createCustomerWithAllRestrictionsExceptAllergies();

    boolean hasAllergy = customerWithoutAllergy.hasAllergy();

    assertFalse(hasAllergy);
  }

  @Test
  public void givenMissingIngredient_whenNewCustomer_thenShouldThrowIngredientNotAvailableException() {
    Executable createCustomerWithMissingIngredients = () -> {
      new Customer(ANY_NAME, ANY_RESTRICTION_LIST, INGREDIENT_LIST_MISSING_REQUIRED_INGREDIENT);
    };

    assertThrows(IngredientNotAvailable.class, createCustomerWithMissingIngredients);
  }
}
