package ca.ulaval.glo4002.reservation.domain.ingredient;

import ca.ulaval.glo4002.reservation.domain.exception.reservationException.IngredientNotAvailable;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IngredientList {
  private List<Ingredient> ingredients;

  public IngredientList() {
    this.ingredients = new ArrayList<>();
  }

  public IngredientList(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public IngredientList(IngredientList ingredientList) {
    this.ingredients = ingredientList.toList();
  }

  public Money getTotalPrice() {
    return ingredients.stream().map(Ingredient::getTotalPrice).reduce(new Money(0, 0, "CAD"), Money::add);
  }

//  public void mergeIngredientWithSameName() {
//    HashMap<String, Ingredient> mergedIngredients = new HashMap();
//    ingredients.forEach(ingredient -> {
//          if(mergedIngredients.containsKey(ingredient.getName())) {
//            mergedIngredients.replace(ingredient.getName(), mergedIngredients.get(ingredient.getName()).withAddedQuantity(ingredient.getQuantity()));
//          } else {
//            mergedIngredients.put(ingredient.getName(), ingredient);
//          }
//        });
//    ingredients = new ArrayList<>(mergedIngredients.values());
//  }

  public Ingredient getByName(String name) throws IngredientNotAvailable {
    return ingredients.stream()
                      .filter(ingredient -> ingredient.getName().equals(name))
                      .findFirst()
                      .orElseThrow(() -> new IngredientNotAvailable());
  }

  public void addAll(IngredientList ingredients) {
    this.ingredients.addAll(ingredients.toList());
  }

  public List<Ingredient> toList() {
    return ingredients;
  }

  public void add(Ingredient ingredient) {
    toList().add(ingredient);
  }

  public boolean isEmpty() {
    return ingredients.isEmpty();
  }

  public boolean isEquals(IngredientList o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Predicate<? super Ingredient> predicat = ingredient -> o.toList().stream()
                                                                        .anyMatch(otherIngredient -> otherIngredient.isEquals(ingredient));
    return toList().stream().allMatch(predicat);
  }

  public boolean containsAllergen(){
      return toList().stream().anyMatch(ingredient -> ingredient.isAllergen());
  }

  public void removeByName(String name) {
    ingredients = ingredients.stream()
                             .filter(ingredient -> !ingredient.getName().toLowerCase().equals(name.toLowerCase()))
                             .collect(Collectors.toList());
  }

  public float getTotalPriceAsFloat() {
    return ingredients.stream().map(Ingredient::getTotalPriceAsFloat).reduce(0f, (f1, f2) -> f1+=f2);
  }

}
