package ca.ulaval.glo4002.reservation.interfaces.rest.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.configuration.ConfigurationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.configurationException.InvalidPeriodDate;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.reservationException.InvalidFormat;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.ConfigurationBodyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ConfigurationBodyValidatorTest {
  final String EARLIER_START_DATE = "2150-03-05";
  final String EARLIER_END_DATE = "2150-03-13";
  final String LATER_START_DATE = "2150-03-15";
  final String LATER_END_DATE = "2150-03-27";
  private ConfigurationBodyValidator configurationBodyValidator;
  private ConfigurationDto configurationDto;

  @BeforeEach
  public void setUp() {
    configurationBodyValidator = new ConfigurationBodyValidator();
    configurationDto = makeValidConfigurationDto();
  }

  @Test
  public void givenValidConfiguration_whenValidatingConfiguration_thenThrowsNothing() {
    Executable validateConfiguration = () -> configurationBodyValidator.validateConfigurationBody(configurationDto);

    assertDoesNotThrow(validateConfiguration);
  }

  @Test
  public void givenInvalidHoppeningStartDate_whenValidatingConfiguration_thenThrowsInvalidFormat() {
    configurationDto.hoppeningStartDate = null;

    Executable validateConfiguration = () -> configurationBodyValidator.validateConfigurationBody(configurationDto);

    assertThrows(InvalidPeriodDate.class, validateConfiguration);
  }

  @Test
  public void givenInvalidHoppeningEndDate_whenValidatingConfiguration_thenThrowsInvalidFormat() {
    configurationDto.hoppeningEndDate = null;

    Executable validateConfiguration = () -> configurationBodyValidator.validateConfigurationBody(configurationDto);

    assertThrows(InvalidPeriodDate.class, validateConfiguration);
  }

  @Test
  public void givenInvalidReservationStartDate_whenValidatingConfiguration_thenThrowsInvalidFormat() {
    configurationDto.reservationStartDate = null;

    Executable validateConfiguration = () -> configurationBodyValidator.validateConfigurationBody(configurationDto);

    assertThrows(InvalidPeriodDate.class, validateConfiguration);
  }

  @Test
  public void givenInvalidReservationEndDate_whenValidatingConfiguration_thenThrowsInvalidFormat() {
    configurationDto.reservationEndDate = null;

    Executable validateConfiguration = () -> configurationBodyValidator.validateConfigurationBody(configurationDto);

    assertThrows(InvalidPeriodDate.class, validateConfiguration);
  }

  @Test
  public void givenExtraFields_whenValidatingConfiguration_thenThrowsInvalidFormat() {
    configurationDto.hasExtraFields = true;

    Executable validateConfiguration = () -> configurationBodyValidator.validateConfigurationBody(configurationDto);

    assertThrows(InvalidFormat.class, validateConfiguration);
  }

  private ConfigurationDto makeValidConfigurationDto() {
    ConfigurationDto configuration = new ConfigurationDto();
    configuration.hoppeningStartDate = LATER_START_DATE;
    configuration.hoppeningEndDate = LATER_END_DATE;
    configuration.reservationStartDate = EARLIER_START_DATE;
    configuration.reservationEndDate = EARLIER_END_DATE;
    configuration.hasExtraFields = false;

    return configuration;
  }
}
