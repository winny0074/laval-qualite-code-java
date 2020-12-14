package ca.ulaval.glo4002.reservation.interfaces.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.configuration.ConfigurationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.ConfigurationBodyValidator;
import ca.ulaval.glo4002.reservation.services.ConfigurationService;
import javax.ws.rs.core.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConfigurationResourceTest {
  private ConfigurationResource configurationResource;
  private ConfigurationService configurationService;
  private ConfigurationDto configurationDto;

  @BeforeEach
  public void setUp() {
    configurationService = mock(ConfigurationService.class);
    ConfigurationBodyValidator configurationBodyValidator =
      mock(ConfigurationBodyValidator.class);
    configurationResource =
      new ConfigurationResource(configurationBodyValidator, configurationService);

    configurationDto = new ConfigurationDto();
  }

  @Test
  public void whenConfiguringDates_thenStatusCodeIsCorrect() {
    Response report = configurationResource.postHoppeningDates(configurationDto);

    assertEquals(HttpStatus.OK_200, report.getStatus());
  }

  @Test
  public void whenConfiguringDates_thenAsksToUpdateDates() {
    configurationResource.postHoppeningDates(configurationDto);

    verify(configurationService).updateHoppeningDates(configurationDto);
  }
}
