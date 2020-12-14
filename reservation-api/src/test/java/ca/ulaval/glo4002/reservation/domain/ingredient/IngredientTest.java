package ca.ulaval.glo4002.reservation.domain.ingredient;

import ca.ulaval.glo4002.reservation.domain.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {
  private final String SOME_NAME = "Tarantula";
  private final Money SOME_PRICE_PER_UNIT = new Money(2,"CAD");
  private final float SOME_QUANTITY = 1.5f;
  private final String DIFFERENT_NAME = "A different kind of " + SOME_NAME;
  private final Money DIFFERENT_PRICE_PER_UNIT = new Money(2,"USD");
  private final float DIFFERENT_QUANTITY = SOME_QUANTITY + 1f;
  private Ingredient baseIngredient;

  @BeforeEach
  public void setupBaseIngredient() {
    baseIngredient = new Ingredient(SOME_NAME, SOME_PRICE_PER_UNIT, SOME_QUANTITY);
  }

  @Test
  public void givenSomeQuantity_whenWithAddedQuantity_thenReturnsNewIngredientWithNewQuantity() {
    Ingredient ingredientWithDoubleQuantity = baseIngredient.withAddedQuantity(SOME_QUANTITY);

    assertEquals(SOME_QUANTITY*2, ingredientWithDoubleQuantity.getQuantity());
  }

  @Test
  public void givenSomeQuantity_whenWithAddedQuantity_thenStaysUnchanged() {
    baseIngredient.withAddedQuantity(SOME_QUANTITY);

    assertEquals(SOME_QUANTITY, baseIngredient.getQuantity());
  }

  @Test
  public void givenIngredientsWithSameNamePriceAndQuantity_whenCheckIfEqual_thenReturnTrue() {
    Ingredient otherIngredient = new Ingredient(baseIngredient.getName(),
                                                new Money(baseIngredient.getPricePerUnit()),
                                                baseIngredient.getQuantity() );

    assertTrue(baseIngredient.isEquals(otherIngredient));
  }

  @Test
  public void givenIngredientsWithDifferentNames_whenCheckIfEqual_thenReturnFalse() {
    Ingredient otherIngredient = new Ingredient(DIFFERENT_NAME,
                                                baseIngredient.getPricePerUnit(),
                                                baseIngredient.getQuantity() );

    assertFalse(baseIngredient.isEquals(otherIngredient));
  }

  @Test
  public void givenIngredientsWithDifferentPricePerUnit_whenCheckIfEqual_thenReturnFalse() {
    Ingredient otherIngredient = new Ingredient(baseIngredient.getName(),
                                                DIFFERENT_PRICE_PER_UNIT,
                                                baseIngredient.getQuantity() );

    assertFalse(baseIngredient.isEquals(otherIngredient));
  }

  @Test
  public void givenIngredientsWithDifferentQuantities_whenCheckIfEqual_thenReturnFalse() {
    Ingredient otherIngredient = new Ingredient(baseIngredient.getName(),
                                                baseIngredient.getPricePerUnit(),
                                                DIFFERENT_QUANTITY );

    assertFalse(baseIngredient.isEquals(otherIngredient));
  }

  @Test
  public void givenNormalTomatoString_whenGettingUnavailableDays_shouldReturnNonZeroDays() {
    String limitedIngredientString = "tomato";
    Ingredient limitedIngredient = new Ingredient(limitedIngredientString, SOME_PRICE_PER_UNIT, SOME_QUANTITY );

    assertTrue(limitedIngredient.getUnavailableDays() != 0);
  }

  @Test
  public void givenMixedCaseTomatoString_whenGettingUnavailableDays_shouldReturnNonZeroDays() {
    String limitedIngredientString = "tOmATO";
    Ingredient limitedIngredient = new Ingredient(limitedIngredientString, SOME_PRICE_PER_UNIT, SOME_QUANTITY );

    assertTrue(limitedIngredient.getUnavailableDays() != 0);
  }

  @Test
  public void givenInvalidTomatoString_whenGettingUnavailableDays_shouldReturnZeroDays() {
    String invalidLimitedIngredientString = "tomatoe";
    Ingredient limitedIngredient = new Ingredient(invalidLimitedIngredientString, SOME_PRICE_PER_UNIT, SOME_QUANTITY );

    assertFalse(limitedIngredient.getUnavailableDays() != 0);
  }

  @Test
  public void givenEmptyTomatoString_whenGettingUnavailableDays_shouldReturnZeroDays() {
    String emptyLimitedIngredientString = "";
    Ingredient limitedIngredient = new Ingredient(emptyLimitedIngredientString, SOME_PRICE_PER_UNIT, SOME_QUANTITY );

    assertFalse(limitedIngredient.getUnavailableDays() != 0);
  }

  @Test
  public void givenNormalCarrotsString_whenMatchAllergen_shouldReturnTrue() {
    String allergenString = "carrots";
    Ingredient allergenIngredient = new Ingredient(allergenString, SOME_PRICE_PER_UNIT, SOME_QUANTITY );

    assertTrue(allergenIngredient.isAllergen());
  }

  @Test
  public void givenMixedCaseCarrotsString_whenMatchAllergen_shouldReturnTrue() {
    String allergenString = "CaRrots";
    Ingredient allergenIngredient = new Ingredient(allergenString, SOME_PRICE_PER_UNIT, SOME_QUANTITY );

    assertTrue(allergenIngredient.isAllergen());
  }

  @Test
  public void givenInvalidCarrotsString_whenMatchCarrot_shouldReturnFalse() {
    String invalidAllergenString = "carrote";
    Ingredient allergenIngredient = new Ingredient(invalidAllergenString, SOME_PRICE_PER_UNIT, SOME_QUANTITY );

    assertFalse(allergenIngredient.isAllergen());
  }

  @Test
  public void givenEmptyCarrotsString_whenMatchCarrot_shouldReturnFalse() {
    String emptyAllergenString = "";
    Ingredient allergenIngredient = new Ingredient(emptyAllergenString, SOME_PRICE_PER_UNIT, SOME_QUANTITY );

    assertFalse(allergenIngredient.isAllergen());
  }
}
