package ca.ulaval.glo4002.reservation.services.assemblers;

import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.IngredientListDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.OrderInUnitsFormatDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.OrderUnitDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderInUnitFormatDtoAssembler {
  private final IngredientsListDtoAssembler ingredientsListDtoAssembler;

  public OrderInUnitFormatDtoAssembler() {
    ingredientsListDtoAssembler = new IngredientsListDtoAssembler();
  }

  public OrderInUnitsFormatDto from(Map<GloDateTime, IngredientList> ingredientListsMap) {
    return new OrderInUnitsFormatDto(makeUnitDtos(ingredientListsMap));
  }

  private List<OrderUnitDto> makeUnitDtos(Map<GloDateTime, IngredientList> ingredientListsMap) {
    List<OrderUnitDto> orderInTotalFormatDtos = new ArrayList<>();
    ingredientListsMap.forEach((date, ingredientList) -> {
      if (!ingredientList.isEmpty()) {
        IngredientListDto ingredientListDto = ingredientsListDtoAssembler.from(ingredientList);
        OrderUnitDto orderInTotalFormatDto = new OrderUnitDto(date.toLocalDateFormat(),
                                                              ingredientListDto.getIngredientsList(),
                                                              ingredientList.getTotalPriceAsFloat());
        orderInTotalFormatDtos.add(orderInTotalFormatDto);
      }
    });
    return orderInTotalFormatDtos;
  }
}
