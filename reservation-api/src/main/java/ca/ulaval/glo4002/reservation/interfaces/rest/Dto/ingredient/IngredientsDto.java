package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.RoundDownAtTwoDigitsSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientsDto {
  public String name;
  public float totalPrice;
  public BigDecimal quantity;

  public IngredientsDto(
      @JsonProperty(value = "name") String name,
      @JsonProperty(value = "totalPrice")
          float totalPrice,
      @JsonProperty(value = "quantity") @JsonSerialize(using = RoundDownAtTwoDigitsSerializer.class)
          BigDecimal quantity) {
    this.name = name;
    this.totalPrice = totalPrice;
    this.quantity = quantity;
  }
}
