package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.country.CountryDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.table.TableDto;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Map;

public class ReservationDto {
  public String vendorCode;
  public String dinnerDate;
  public ArrayList<TableDto> tableDtos;
  public String creationDate;
  public CountryDto country;
  public boolean hasExtraFields;

  public ReservationDto() {}

  @JsonCreator
  public ReservationDto(
      @JsonProperty(value = "vendorCode", defaultValue = "") String vendorCode,
      @JsonProperty(value = "dinnerDate", defaultValue = "") String dinnerDate,
      @JsonProperty(value = "tables") ArrayList<TableDto> tableDtos,
      @JsonProperty(value = "from") Map<String, Object> fromInfo)
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    this.vendorCode = vendorCode;
    this.dinnerDate = dinnerDate;
    this.tableDtos = tableDtos;
    if (fromInfo != null) {
      this.creationDate = (String) fromInfo.get("reservationDate");
      this.country =
          objectMapper.readValue(
              objectMapper.writeValueAsString(fromInfo.get("country")), CountryDto.class);
      if (fromInfo.size() > 2) {
        hasExtraFields = true;
      }
    } else {
      this.creationDate = "";
      this.country = null;
    }
  }

  @JsonAnySetter
  public void ignored(String name, Object value) {
    hasExtraFields = true;
  }
}
