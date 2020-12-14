package ca.ulaval.glo4002.reservation.domain.reservation;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.domain.ingredient.meal.MealType;
import ca.ulaval.glo4002.reservation.domain.money.Money;

import java.util.LinkedList;
import java.util.List;

public class ReservationMother {
  public static Reservation createBasicReservation() {
    return Reservation.from(ReservationRequestMother.createBasicRequest(),
                            makeListOfAllIngredientsInRestrictions(),
                            ReservationNumber.create("TEAM-123"));
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
