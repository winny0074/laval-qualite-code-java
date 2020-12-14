package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.table;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.customer.CustomerDto;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TableDto {
  public ArrayList<CustomerDto> customers;
  public boolean hasExtraFields;

  @JsonCreator
  public TableDto(@JsonProperty(value = "customers") ArrayList<CustomerDto> customers) {
    this.customers = customers;
  }

  public TableDto() {}

  @JsonAnySetter
  public void ignored(String name, Object value) {
    hasExtraFields = true;
  }
}
