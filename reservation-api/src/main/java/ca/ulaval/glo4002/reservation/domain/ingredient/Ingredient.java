package ca.ulaval.glo4002.reservation.domain.ingredient;

import ca.ulaval.glo4002.reservation.domain.money.Money;

import java.util.Objects;

public class Ingredient {
  private final String name;
  private final float quantity;
  private final Money pricePerUnit;

  public Ingredient(String name, Money pricePerUnit, float quantity) {
    this.name = name;
    this.quantity = quantity;
    this.pricePerUnit = pricePerUnit;
  }

  public Money getTotalPrice() {
    return pricePerUnit.multiply(getQuantity());
  }

  public String getName() {
    return name;
  }

  public float getQuantity() {
    return quantity;
  }

  public Money getPricePerUnit() { return pricePerUnit; }

  public Ingredient withQuantity(float quantity) {
    return new Ingredient(name, pricePerUnit, quantity);
  }

  public Ingredient withAddedQuantity(float quantity) {
    return new Ingredient(name, pricePerUnit, this.quantity + quantity);
  }

  // Don't delete - Used indirectly when List<Ingredient>::isEquals is called
//  public boolean isEquals(Ingredient otherIngredient) {
//    return otherIngredient.name.isEquals(name)
//        && otherIngredient.quantity == quantity
//        && otherIngredient.pricePerUnit.isEqual(pricePerUnit);
//  }

//  @Override
//  public int compareTo(Ingredient ingredient) {
//    int comparison = name.compareTo(ingredient.getName());
//    if (comparison == 0) {
//      if (quantity < ingredient.getQuantity()) {
//        return -1;
//      } else if (quantity > ingredient.getQuantity()) {
//        return 1;
//      } else if (pricePerUnit.isEqual(ingredient.getPricePerUnit())) {
//        return 0;
//      } else {
//        return -2;
//      }
//    } else {
//      return comparison;
//    }
//  }

  public int getUnavailableDays() {
    for(LimitedIngredient limitedIngredient: LimitedIngredient.values()) {
      if(matchIngredientName(limitedIngredient.getName())) {
        return limitedIngredient.getUnavailableDurationInDays();
      }
    }

    return 0;
  }

  public boolean isAllergen() {
    boolean matchFound = false;

    for(Allergen allergen: Allergen.values()) {
      matchFound = matchIngredientName(allergen.getName());
      if(matchFound) {
        break;
      }
    }

    return  matchFound;
  }

  private boolean matchIngredientName(String otherIngredientName) {
    return getName().toLowerCase().equals(otherIngredientName.toLowerCase());
  }

  public float getTotalPriceAsFloat() {
    return pricePerUnit.toFloat() * getQuantity();
  }

  public boolean isEquals(Ingredient o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    return Float.compare(o.quantity, quantity) == 0 && name.equals(o.name) && pricePerUnit.isEqual(o.pricePerUnit);
  }
}
