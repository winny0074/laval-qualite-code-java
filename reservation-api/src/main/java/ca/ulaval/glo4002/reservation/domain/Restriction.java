package ca.ulaval.glo4002.reservation.domain;

import ca.ulaval.glo4002.reservation.domain.ingredient.meal.Beverage;
import ca.ulaval.glo4002.reservation.domain.ingredient.meal.Dessert;
import ca.ulaval.glo4002.reservation.domain.ingredient.meal.Entree;
import ca.ulaval.glo4002.reservation.domain.ingredient.meal.MainDish;
import ca.ulaval.glo4002.reservation.domain.ingredient.meal.*;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Restriction {
  AUCUNE(
      "aucune",
      new Money(0, 0, "CAD"),
      new MainDish("Pepperoni", 10),
      new Dessert("Roast beef", 5),
      new Beverage("Water", 0.1f),
      new Entree("Pork loin", 5),
      new Entree("Carrots", 8)),

  VEGETARIENNE(
      "vegetarian",
      new Money(500, 0, "CAD"),
      new MainDish("Tuna", 10),
      new Dessert("Mozzarella", 5),
      new Beverage("Water", 0.1f),
      new Entree("Pumpkin", 5),
      new Entree("Chocolate", 8)),

  VEGETALIENNE(
      "vegan",
      new Money(1000, 0, "CAD"),
      new MainDish("Kimchi", 10),
      new Dessert("Worcestershire sauce", 5),
      new Beverage("Water", 0.1f),
      new Entree("Tomato", 5),
      new Entree("Kiwi", 8)),

  ALLERGIE(
      "allergies",
      new Money(0, 0, "CAD"),
      new MainDish("Tofu", 10),
      new Dessert("Bacon", 5),
      new Beverage("Water", 0.1f),
      new Entree("Marmalade", 5),
      new Entree("Plantain", 8)),

  MALADIE(
      "illness",
      new Money(0, 0, "CAD"),
      new MainDish("Kiwi", 5),
      new Dessert("Pepperoni", 2),
      new Beverage("Water", 0.1f),
      new Entree("Scallops", 2),
      new Entree("Butternut squash", 4));

  private static final int BASE_PRICE_UNITS = 1000;
  private static final int BASE_PRICE_CENTS = 0;
  private static final String BASE_PRICE_CURRENCY = "CAD";
  private final Money price;
  private final String name;
  private final List<MealType> meals;

  Restriction(
      String name,
      Money price,
      MealType ... meals) {
    this.name = name;
    this.price = price;
    this.meals = Arrays.asList(meals);
  }

  public static Restriction fromName(String givenName) {
    for (Restriction restriction : Restriction.values()) {
      if (restriction.toString().equals(givenName)) {
        return restriction;
      }
    }
    throw new IllegalArgumentException(givenName);
  }

  public static List<String> getRestrictionNames() {
    return Stream.of(Restriction.values()).map(x -> x.name).collect(Collectors.toList());
  }

  public static Money getBasePrice() {
    return new Money(BASE_PRICE_UNITS, BASE_PRICE_CENTS, BASE_PRICE_CURRENCY);
  }

  public List<String> getMealNames() {
    return getMeals().stream().map(MealType::getName).collect(Collectors.toList());
  }

  public Money getPrice() {
    return price;
  }

  public String toString() {
    return name;
  }

  public List<MealType> getMeals() {
    return meals;
  }
}
