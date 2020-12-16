package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OrderInUnitsFormatDto {

  @JsonProperty("Dates")
  public List<OrderUnitDto> orders = new ArrayList<>();

  public OrderInUnitsFormatDto(@JsonProperty(value = "Dates") List<OrderUnitDto> orders) {
    this.orders = orders;
  }
}
