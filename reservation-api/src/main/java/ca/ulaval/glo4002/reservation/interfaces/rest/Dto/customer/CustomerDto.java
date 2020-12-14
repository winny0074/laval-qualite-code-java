package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.customer;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDto {
  public String name;
  public String[] restrictions;

  @JsonIgnore public boolean hasExtraFields;

  @JsonCreator
  public CustomerDto(
      @JsonProperty(value = "name", defaultValue = "") String name,
      @JsonProperty(value = "restrictions") String[] restrictions) {
    this.name = name;
    this.restrictions = restrictions;
  }

  public CustomerDto() {}

  @JsonAnySetter
  public void ignored(String name, Object value) {
    hasExtraFields = true;
  }
}
