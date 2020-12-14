package ca.ulaval.glo4002.reservation.interfaces.rest;

import ca.ulaval.glo4002.reservation.domain.exception.ingredientsReportsException.InvalidType;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.OrderInTotalFormatDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.OrderInUnitsFormatDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.IngredientsReportsParametersValidator;
import ca.ulaval.glo4002.reservation.services.IngredientsService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("reports/ingredients")
public class IngredientsReportsResource {

  public static final String TYPE_UNIT = "unit";
  public static final String TYPE_TOTAL = "total";
  private final IngredientsService ingredientsService;
  private final IngredientsReportsParametersValidator ingredientsReportsParametersValidator;

  public IngredientsReportsResource() {
    ingredientsService = new IngredientsService();
    ingredientsReportsParametersValidator = new IngredientsReportsParametersValidator();
  }

  public IngredientsReportsResource(
      IngredientsService ingredientsService,
      IngredientsReportsParametersValidator ingredientsReportsParametersValidator) {
    this.ingredientsService = ingredientsService;
    this.ingredientsReportsParametersValidator = ingredientsReportsParametersValidator;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getIngredients(
      @QueryParam("startDate") String startDate,
      @QueryParam("endDate") String endDate,
      @QueryParam("type") String type) {

    ingredientsReportsParametersValidator.validateIngredientsReportsParameters(startDate, endDate, type);

    if (type.equals(TYPE_UNIT)) {
      OrderInUnitsFormatDto data =
          ingredientsService.getIngredientsToOrderInUnitsFormat(startDate, endDate);
      return Response.ok(data).build();
    } else if (type.equals(TYPE_TOTAL)) {
      OrderInTotalFormatDto data =
          ingredientsService.getIngredientsToOrderInTotalFormat(startDate, endDate);
      return Response.ok(data).build();
    } else {
      throw new InvalidType();
    }
  }
}
