package ca.ulaval.glo4002.reservation.domain.customer;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.IngredientNotAvailable;
import ca.ulaval.glo4002.reservation.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import ca.ulaval.glo4002.reservation.domain.ingredient.meal.MealType;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Customer {
  private final String name;
  private final List<Restriction> restrictions;
  private final Collection<Ingredient> ingredients;

  public Customer(String name, List<Restriction> restrictions, IngredientList availableIngredients) throws IngredientNotAvailable {
    this.name = name;
    this.restrictions = restrictions;
    this.ingredients = computeIngredients(availableIngredients);
  }

  private Collection<Ingredient> computeIngredients(IngredientList availableIngredients) throws IngredientNotAvailable {
    Collection<Ingredient> ingredients = new LinkedList<>();
    for (MealType meal : getMeals()) {
      Ingredient ingredient = availableIngredients.getByName(meal.getName()).withQuantity(meal.getQuantity());
      ingredients.add(ingredient);
    }
    return ingredients;
  }

  public Collection<Ingredient> getIngredients() { return ingredients; }

  public String getName() {
    return this.name;
  }

  public List<Restriction> getRestrictions() {
    return this.restrictions;
  }

  public Money getPrice() {
    Money price = new Money(Restriction.getBasePrice());
    for (Restriction restriction : this.restrictions) {
      price = price.add(restriction.getPrice());
    }
    return price;
  }

  public boolean hasAllergy() {
    return restrictions.stream().anyMatch(restriction -> restriction.equals(Restriction.ALLERGIE));
  }
  
  private List<MealType> getMeals() {
    if (restrictions.isEmpty()) {
      return Restriction.AUCUNE.getMeals();
    } else {
      return restrictions.stream()
                         .flatMap(restriction -> restriction.getMeals().stream())
                         .collect(Collectors.toList());
    }
  }
}
