package ca.ulaval.glo4002.reservation.infrastructure.supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierIngredientDto {
  public String name;

  @JsonIgnore public Integer id;
  @JsonIgnore public Float pricePerKg;
  @JsonIgnore public String origin;
  @JsonIgnore public List<ShipmentDto> shipments;

  public SupplierIngredientDto(
      @JsonProperty(value = "name") String name,
      @JsonProperty(value = "id") Integer id,
      @JsonProperty(value = "pricePerKg") Float pricePerKg,
      @JsonProperty(value = "origin") String origin,
      @JsonProperty(value = "shipments") List<ShipmentDto> shipments) {
    this.name = name;
    this.id = id;
    this.pricePerKg = pricePerKg;
    this.origin = origin;
    this.shipments = shipments;
  }
}
