package ca.ulaval.glo4002.reservation.domain.ingredient;

import ca.ulaval.glo4002.reservation.domain.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientListTest {
  private static final String INGREDIENT_NAME = "Tarantula";
  private static final String ALLERGEN = "Carrots";
  private static final String A_DIFFERENT_INGREDIENT_NAME = "Another kind of " + INGREDIENT_NAME;
  private IngredientList ingredientList;
  private Money ANY_PRICE = new Money(10, 10, "CAD");
  private Money EXPECTED_TOTAL_PRICE_OF_TWO_INGREDIENTS_AS_MONEY = new Money(20, 20, "CAD");
  private float EXPECTED_TOTAL_PRICE_OF_TWO_INGREDIENTS_AS_FLOAT = 20.20f;
  private long ANY_QUANTITY = 1L;
  private Ingredient anIngredient;
  private Ingredient aDifferentIngredient;

  @BeforeEach
  public void setUp() {
    ingredientList = new IngredientList();
    anIngredient = new Ingredient(INGREDIENT_NAME, ANY_PRICE, ANY_QUANTITY);
    aDifferentIngredient = new Ingredient(A_DIFFERENT_INGREDIENT_NAME, ANY_PRICE, ANY_QUANTITY);
  }

  @Test
  public void givenTwoSimilarIngredientListsWithElementsInDifferentOrder_whenIsEquals_thenReturnTrue() {
    IngredientList firstIngredientList = new IngredientList(List.of(anIngredient, aDifferentIngredient));

    IngredientList secondIngredientList = new IngredientList(List.of(aDifferentIngredient, anIngredient));

    assertTrue(firstIngredientList.isEquals(secondIngredientList));
  }

  @Test
  public void givenTwoSimilarIngredientListsButOneHasNonMatchingDuplicates_whenIsEquals_thenAreNotEqual() {
    IngredientList firstIngredientList = new IngredientList(List.of(anIngredient, aDifferentIngredient));

    IngredientList secondIngredientList = new IngredientList(List.of(anIngredient, aDifferentIngredient, aDifferentIngredient));

    assertFalse(!firstIngredientList.isEquals(secondIngredientList));
  }

//  @Test
//  public void givenNoDuplicateIngredients_whenMerging_thenListDoesNotChange() {
//    ingredientList.addAll(new IngredientList(List.of(anIngredient, aDifferentIngredient)));
//
//    IngredientList originalList = new IngredientList(ingredientList);
//    ingredientList.mergeIngredientWithSameName();
//
//    assertTrue(originalList.isEquals(ingredientList));
//  }
//
//  @Test
//  public void givenDuplicateIngredients_whenMerging_thenRemoveDuplicatesFromList() {
//    this.ingredientList =
//            new IngredientList(List.of(anIngredient, anIngredient, aDifferentIngredient, aDifferentIngredient));
//
//    ingredientList.mergeIngredientWithSameName();
//
//    assertEquals(2, ingredientList.toList().size());
//  }
//
//  @Test
//  public void givenDuplicateIngredients_whenMerging_thenAddDuplicatesQuantityToExistingQuantity() {
//    long quantity1 = 10L;
//    long quantity2 = 4L;
//    prepareIngredientListWithQuantitiesOfSameIngredient(quantity1, quantity2);
//
//    ingredientList.mergeIngredientWithSameName();
//
//    assertEquals(quantity1 + quantity2, ingredientList.getByName(anIngredient.getName()).getQuantity());
//  }

  @Test
  public void givenAnIngredientList_whenGetTotalPrice_thenReturnCorrectAmount() {
    IngredientList ingredientListWithTwoSameIngredient = new IngredientList(List.of(anIngredient, anIngredient));

    Money totalPrice = ingredientListWithTwoSameIngredient.getTotalPrice();

    assertTrue(totalPrice.isEqual(EXPECTED_TOTAL_PRICE_OF_TWO_INGREDIENTS_AS_MONEY));
  }

  @Test
  public void givenAnIngredientListWithAllergen_whenCheckIfContainsAllergen_thenReturnTrue() {
    this.ingredientList = new IngredientList(List.of(new Ingredient(ALLERGEN, ANY_PRICE, ANY_QUANTITY)));

    boolean containsAllergen = this.ingredientList.containsAllergen();

    assertTrue(containsAllergen);
  }

  @Test
  public void givenAnIngredientListWithoutAllergen_whenCheckIfContainsAllergen_thenReturnFalse() {
    this.ingredientList = new IngredientList(List.of(anIngredient));

    boolean containsAllergen = this.ingredientList.containsAllergen();

    assertFalse(containsAllergen);
  }

  @Test
  public void givenAnIngredientInIngredientList_whenRemoveIngredientByName_thenReturnIngredientListWithoutIngredientWithSameName() {
    this.ingredientList = new IngredientList(List.of(anIngredient, aDifferentIngredient));

    this.ingredientList.removeByName(anIngredient.getName());

    assertEquals(1, this.ingredientList.toList().size());
    assertSame(aDifferentIngredient, this.ingredientList.toList().get(0));
  }

  @Test
  public void givenIngredientList_whenGetTotalPriceAsFloat_thenReturnCorrectAmount() {
    IngredientList ingredientListWithTwoSameIngredient = new IngredientList(List.of(anIngredient, anIngredient));

    float totalPrice = ingredientListWithTwoSameIngredient.getTotalPriceAsFloat();

    assertEquals(EXPECTED_TOTAL_PRICE_OF_TWO_INGREDIENTS_AS_FLOAT, totalPrice);
  }

//  private void prepareIngredientListWithQuantitiesOfSameIngredient(long... quantities) {
//    List<Ingredient> ingredients = new ArrayList<>();
//    for (long quantity : quantities) {
//      ingredients.add(anIngredient.withQuantity(quantity));
//    }
//    ingredientList = new IngredientList(ingredients);
//  }
}
