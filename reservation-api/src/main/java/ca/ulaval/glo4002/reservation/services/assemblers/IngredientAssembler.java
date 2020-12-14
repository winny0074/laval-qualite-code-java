package ca.ulaval.glo4002.reservation.services.assemblers;

import ca.ulaval.glo4002.reservation.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import ca.ulaval.glo4002.reservation.infrastructure.supplier.SupplierIngredientDto;

import java.util.LinkedList;
import java.util.List;

public class IngredientAssembler {
  public List<Ingredient> from(List<SupplierIngredientDto> dtos) {
    List<Ingredient> ingredients = new LinkedList<>();
    for(SupplierIngredientDto dto : dtos) {
      ingredients.add(assembleOneFromDto(dto));
    }
    return ingredients;
  }

  private Ingredient assembleOneFromDto(SupplierIngredientDto dto) {
    return new Ingredient(dto.name, new Money(dto.pricePerKg, "CAD"), 0f);
  }
}
