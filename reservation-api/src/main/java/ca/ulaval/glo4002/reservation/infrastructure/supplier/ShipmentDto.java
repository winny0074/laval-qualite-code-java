package ca.ulaval.glo4002.reservation.infrastructure.supplier;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.IngredientsDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ShipmentDto {
  public Integer id;
  public ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.IngredientsDto ingredient;
  public String quantity;
  public LocalDateTime date;

  public ShipmentDto(
      @JsonProperty(value = "id") Integer id,
      @JsonProperty(value = "ingredient") IngredientsDto ingredient,
      @JsonProperty(value = "quantity") String quantity,
      @JsonProperty(value = "date") LocalDateTime date) {
    this.id = id;
    this.ingredient = ingredient;
    this.quantity = quantity;
    this.date = date;
  }
}
