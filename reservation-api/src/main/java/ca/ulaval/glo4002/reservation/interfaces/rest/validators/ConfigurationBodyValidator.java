package ca.ulaval.glo4002.reservation.interfaces.rest.validators;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.configuration.ConfigurationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.InterfacesException;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.configurationException.InvalidPeriodDate;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.reservationException.InvalidFormat;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.common.StringValidator;

public class ConfigurationBodyValidator {
  public ConfigurationBodyValidator() {}

  public void validateConfigurationBody(ConfigurationDto configurationDto) throws InterfacesException {

    validateExtraFields(configurationDto.hasExtraFields);
    validateDate(configurationDto.hoppeningStartDate);
    validateDate(configurationDto.hoppeningEndDate);
    validateDate(configurationDto.reservationStartDate);
    validateDate(configurationDto.reservationEndDate);
  }

  private void validateExtraFields(boolean hasExtraFields) {
    if (hasExtraFields) {
      throw new InvalidFormat();
    }
  }

  private void validateDate(String date) {
    StringValidator.isNullOrEmpty(date, new InvalidPeriodDate());
  }
}
