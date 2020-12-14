package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.country;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryDto {
  public String code;
  public String fullname;
  public String currency;
  public boolean hasExtraFields;

  @JsonCreator
  public CountryDto(
      @JsonProperty(value = "code", defaultValue = "") String code,
      @JsonProperty(value = "fullname", defaultValue = "") String fullname,
      @JsonProperty(value = "currency", defaultValue = "") String currency) {
    this.code = code;
    this.fullname = fullname;
    this.currency = currency;
  }

  public CountryDto() {}

  @JsonAnySetter
  public void ignored(String name, Object value) {
    hasExtraFields = true;
  }
}
