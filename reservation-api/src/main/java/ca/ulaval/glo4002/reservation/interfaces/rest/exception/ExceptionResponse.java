package ca.ulaval.glo4002.reservation.interfaces.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"localizedMessage", "suppressed", "message", "stackTrace", "cause"})
public class ExceptionResponse {
  private final String error;
  private final String description;
  @JsonIgnore
  private final int statusCode;

  public ExceptionResponse(String error, String description, int statusCode) {
    this.error = error;
    this.description = description;
    this.statusCode = statusCode;
  }

  public String getError() {
    return error;
  }

  public String getDescription() {
    return description;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
