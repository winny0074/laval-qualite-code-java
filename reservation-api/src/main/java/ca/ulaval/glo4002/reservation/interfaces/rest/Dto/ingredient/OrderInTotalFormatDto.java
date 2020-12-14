package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderInTotalFormatDto {
  @JsonProperty("ingredients")
  public List<IngredientsDto> ingredientList;

  @JsonProperty(value = "totalPrice")
  public float totalPrice;

  public OrderInTotalFormatDto(List<IngredientsDto> ingredientList, float totalPrice) {
    this.ingredientList = ingredientList;
    this.totalPrice = totalPrice;
  }
}
