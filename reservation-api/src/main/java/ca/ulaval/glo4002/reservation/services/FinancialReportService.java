package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.restaurant.RestaurantConfiguration;
import ca.ulaval.glo4002.reservation.infrastructure.RestaurantContextPersistenceInMemory;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.FinancialRapport.FinancialReportDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.ingredient.OrderInTotalFormatDto;

import javax.inject.Inject;
import java.math.BigDecimal;

public class FinancialReportService {
  private ReservationService reservationService;
  private ChefService                 chefService;
  private IngredientsService ingredientsService;

  @Inject
  public FinancialReportService(ReservationService reservationService, ChefService chefService, IngredientsService ingredientsService) {
    this.reservationService = reservationService;
    this.chefService = chefService;
    this.ingredientsService = ingredientsService;
  }

  public FinancialReportDto getReport() {

    BigDecimal income = getIncome();
    BigDecimal expense = getExpense();
    BigDecimal profit = income.subtract(expense);

    return new FinancialReportDto(expense, income, profit);
  }

  private BigDecimal getIncome() {
    double reservationTotalPrice = reservationService.getReservationTotalPrice();
    return BigDecimal.valueOf(reservationTotalPrice);
  }

  private BigDecimal getExpense() {
    BigDecimal globalChefReport = chefService.getTotalExpense();

    BigDecimal ingredientsTotalOrder = ingredientsService.getTotalExpense();

    return globalChefReport.add(ingredientsTotalOrder).abs();

  }
}
