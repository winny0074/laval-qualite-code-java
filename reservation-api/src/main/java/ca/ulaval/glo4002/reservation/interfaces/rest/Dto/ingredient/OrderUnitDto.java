package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.RoundDownAtTwoDigitsSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.List;

public class OrderUnitDto {
  @JsonProperty(value = "date")
  public String date;

  @JsonProperty("ingredients")
  public List<IngredientsDto> ingredientList;

  @JsonProperty(value = "totalPrice")
  public float totalPrice;

  public OrderUnitDto(String date, List<IngredientsDto> ingredientList, float totalPrice) {
    this.date = date;
    this.ingredientList = ingredientList;
    this.totalPrice = totalPrice;
  }

  public float getTotalPrice() {
    return totalPrice;
  }
}
