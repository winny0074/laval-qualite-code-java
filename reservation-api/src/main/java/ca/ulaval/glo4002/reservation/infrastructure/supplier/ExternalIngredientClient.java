package ca.ulaval.glo4002.reservation.infrastructure.supplier;

import ca.ulaval.glo4002.reservation.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientRepository;
import ca.ulaval.glo4002.reservation.services.assemblers.IngredientAssembler;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExternalIngredientClient implements IngredientRepository {
  private final String INGREDIENT_SUPPLIER_URL = "http://localhost:8080/ingredients";

  public IngredientList findAll() {
    List<SupplierIngredientDto> dtos;
    Client client = ClientBuilder.newClient();

    WebTarget target = client.target(INGREDIENT_SUPPLIER_URL);

    try {
      dtos = Arrays.asList(target.request()
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get()
                                 .readEntity(SupplierIngredientDto[].class));
    } catch (Exception e) {
      dtos = Collections.emptyList();
    }
    IngredientAssembler assembler = new IngredientAssembler();
    List<Ingredient> ingredients = assembler.from(dtos);

    return new IngredientList(ingredients);
  }
}
