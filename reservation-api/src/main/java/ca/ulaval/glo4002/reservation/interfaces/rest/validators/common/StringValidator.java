package ca.ulaval.glo4002.reservation.interfaces.rest.validators.common;

import java.util.Objects;

public class StringValidator {
  public static void isNullOrEmpty(String paramName, RuntimeException error) {
    if (Objects.isNull(paramName) || paramName.isEmpty()) {
      throw error;
    }
  }
}
