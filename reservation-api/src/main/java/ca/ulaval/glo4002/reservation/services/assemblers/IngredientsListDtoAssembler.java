package ca.ulaval.glo4002.reservation.services.assemblers;

import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.IngredientListDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.IngredientsDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class IngredientsListDtoAssembler {
  public IngredientListDto from(IngredientList ingredientList) {
    List<IngredientsDto> ingredientsDtoList =
        ingredientList.toList().stream()
            .map(
                ingredient ->
                    new IngredientsDto(
                        ingredient.getName(),
                        ingredient.getTotalPriceAsFloat(),
                        BigDecimal.valueOf(ingredient.getQuantity())))
            .collect(Collectors.toList());
    return new IngredientListDto(ingredientsDtoList, ingredientList.getTotalPrice().getUnits());
  }
}
