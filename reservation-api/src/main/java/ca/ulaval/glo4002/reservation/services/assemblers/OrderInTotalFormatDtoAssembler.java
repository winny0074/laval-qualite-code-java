package ca.ulaval.glo4002.reservation.services.assemblers;

import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.IngredientListDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.IngredientsDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.OrderInTotalFormatDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderInTotalFormatDtoAssembler {
  private final IngredientsListDtoAssembler ingredientsListDtoAssembler;

  public OrderInTotalFormatDtoAssembler() {
    ingredientsListDtoAssembler = new IngredientsListDtoAssembler();
  }

  public OrderInTotalFormatDto from(Map<GloDateTime, IngredientList> ingredientListMap) {

    List<IngredientsDto> ingredientsDtos = new ArrayList<>();
    IngredientList totalIngredientList = new IngredientList();

    ingredientListMap.entrySet()
                     .stream()
                     .forEach(entry -> {
                       IngredientList ingredientList = entry.getValue();
                       totalIngredientList.addAll(ingredientList);
                       IngredientListDto ingredientListDto = ingredientsListDtoAssembler.from(ingredientList);
                       ingredientsDtos.addAll(ingredientListDto.getIngredientsList());
                       });
    return new OrderInTotalFormatDto(ingredientsDtos, totalIngredientList.getTotalPriceAsFloat());
  }
}
