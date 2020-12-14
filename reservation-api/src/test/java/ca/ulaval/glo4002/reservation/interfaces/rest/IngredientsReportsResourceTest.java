package ca.ulaval.glo4002.reservation.interfaces.rest;

import ca.ulaval.glo4002.reservation.domain.exception.ingredientsReportsException.InvalidType;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.IngredientsReportsParametersValidator;
import ca.ulaval.glo4002.reservation.services.IngredientsService;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class IngredientsReportsResourceTest {
  private final String START_DATE = "2150-07-21";
  private final String END_DATE = "2150-07-23";

  private IngredientsReportsResource ingredientsReportsResource;
  private IngredientsService ingredientsService;

  @BeforeEach
  public void setUp() {
    ingredientsService = mock(IngredientsService.class);
    IngredientsReportsParametersValidator ingredientsReportsParametersValidator =
        mock(IngredientsReportsParametersValidator.class);
    ingredientsReportsResource =
        new IngredientsReportsResource(ingredientsService, ingredientsReportsParametersValidator);
  }

  @Test
  public void whenGettingAnIngredientsReport_thenStatusCodeIsCorrect() {
    Response report =
        ingredientsReportsResource.getIngredients(
            START_DATE, END_DATE, IngredientsReportsResource.TYPE_TOTAL);

    assertEquals(HttpStatus.OK_200, report.getStatus());
  }

  @Test
  public void whenGettingAnIngredientsReportOfTypeUnit_thenGetsCorrectTypeOfReport() {
    ingredientsReportsResource.getIngredients(
        START_DATE, END_DATE, IngredientsReportsResource.TYPE_UNIT);

    verify(ingredientsService).getIngredientsToOrderInUnitsFormat(START_DATE, END_DATE);
  }

  @Test
  public void whenGettingAnIngredientsReportOfTypeTotal_thenGetsCorrectTypeOfReport() {
    ingredientsReportsResource.getIngredients(
        START_DATE, END_DATE, IngredientsReportsResource.TYPE_TOTAL);

    verify(ingredientsService).getIngredientsToOrderInTotalFormat(START_DATE, END_DATE);
  }

  @Test
  public void givenNoIngredientReportType_whenGettingReport_thenThrowsInvalidTypeError() {
    String reportType = "";

    assertThrows(
        InvalidType.class,
        () -> ingredientsReportsResource.getIngredients(START_DATE, END_DATE, reportType));
  }
}
