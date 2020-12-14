package ca.ulaval.glo4002.reservation.domain.customer;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.domain.ingredient.meal.MealType;
import ca.ulaval.glo4002.reservation.domain.money.Money;

import java.util.LinkedList;
import java.util.List;

public class CustomerMother {
  private static String ANY_NAME = "John Doe";

  public static Customer createCustomerWithoutRestrictions() {
    return new Customer(ANY_NAME, new LinkedList<>(), makeListOfAllIngredientsInRestrictions());
  }

  public static Customer createCustomerWithRestrictions() {
    List<Restriction> restrictions = List.of(Restriction.VEGETARIENNE, Restriction.MALADIE, Restriction.VEGETALIENNE);
    return new Customer(ANY_NAME, restrictions, makeListOfAllIngredientsInRestrictions());
  }

  public static Customer createCustomerWithAllergies() {
    List<Restriction> restrictions = List.of(Restriction.ALLERGIE);
    return new Customer(ANY_NAME, restrictions, makeListOfAllIngredientsInRestrictions());
  }

  public static Customer createCustomerWithAllRestrictionsExceptAllergies() {
    List<Restriction> restrictions = new LinkedList<>(List.of(Restriction.values()));
    restrictions.remove(Restriction.ALLERGIE);
    return new Customer(ANY_NAME, restrictions, makeListOfAllIngredientsInRestrictions());
  }

  private static IngredientList makeListOfAllIngredientsInRestrictions() {
    List<Ingredient> ingredients = new LinkedList<>();
    for (Restriction restriction : Restriction.values()) {
      for (MealType meal : restriction.getMeals()) {
        ingredients.add(new Ingredient(meal.getName(), new Money(0, 0, ""), 0));
      }
    }
    return new IngredientList(ingredients);
  }
}
