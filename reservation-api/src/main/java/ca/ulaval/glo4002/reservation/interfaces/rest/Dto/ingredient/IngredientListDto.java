package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class IngredientListDto {

  public List<IngredientsDto> ingredientsList = new ArrayList<>();
  public long totalPrice;

  public IngredientListDto(
      @JsonProperty(value = "ingredients") List<IngredientsDto> ingredientsList,
      @JsonProperty(value = "totalPrice") long totalPrice) {
    this.setIngredientsList(ingredientsList);
    this.setTotalPrice(totalPrice);
  }

  public List<IngredientsDto> getIngredientsList() {
    return ingredientsList;
  }

  public void setIngredientsList(List<IngredientsDto> ingredientsList) {
    this.ingredientsList = ingredientsList;
  }

  public long getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(long totalPrice) {
    this.totalPrice = totalPrice;
  }
}
