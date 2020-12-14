package ca.ulaval.glo4002.reservation.interfaces.rest;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.configuration.ConfigurationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.ConfigurationBodyValidator;
import ca.ulaval.glo4002.reservation.services.ConfigurationService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/configuration")
public class ConfigurationResource {
  private final ConfigurationService configurationService;
  private final ConfigurationBodyValidator configurationBodyValidator;

  public ConfigurationResource() {
    this.configurationBodyValidator = new ConfigurationBodyValidator();
    this.configurationService = new ConfigurationService();
  }

  public ConfigurationResource(
    ConfigurationBodyValidator configurationBodyValidator,
    ConfigurationService configurationService) {
    this.configurationBodyValidator = configurationBodyValidator;
    this.configurationService = configurationService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postHoppeningDates(ConfigurationDto configuration) {
    configurationBodyValidator.validateConfigurationBody(configuration);

    configurationService.updateHoppeningDates(configuration);

    return Response.ok()
      .status(Status.OK)
      .build();
  }
}

