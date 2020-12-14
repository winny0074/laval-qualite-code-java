package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.configuration;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;

public class ConfigurationDto {
  private final String USE_OLD_DATE_INDICATOR = "OLD_DATE";
  public String hoppeningStartDate;
  public String hoppeningEndDate;
  public String reservationStartDate;
  public String reservationEndDate;
  public boolean hasExtraFields;

  public ConfigurationDto() {}

  @JsonCreator
  public ConfigurationDto(
    @JsonProperty(value = "hoppening") Map<String, Object> hoppeningDates,
    @JsonProperty(value = "reservationPeriod") Map<String, Object> reservationDates)
    throws JsonProcessingException {
    if (hoppeningDates != null) {
      this.hoppeningStartDate = (String) hoppeningDates.get("beginDate");
      this.hoppeningEndDate = (String) hoppeningDates.get("endDate");
      if (hoppeningDates.size() > 2) {
        hasExtraFields = true;
      }
    } else {
      this.hoppeningStartDate = USE_OLD_DATE_INDICATOR;
      this.hoppeningEndDate = USE_OLD_DATE_INDICATOR;
    }
    if (reservationDates != null) {
      this.reservationStartDate = (String) reservationDates.get("beginDate");
      this.reservationEndDate = (String) reservationDates.get("endDate");
      if (reservationDates.size() > 2) {
        hasExtraFields = true;
      }
    } else {
      this.reservationStartDate = USE_OLD_DATE_INDICATOR;
      this.reservationEndDate = USE_OLD_DATE_INDICATOR;
    }
  }

  @JsonAnySetter
  public void ignored(String name, Object value) {
    hasExtraFields = true;
  }
}
