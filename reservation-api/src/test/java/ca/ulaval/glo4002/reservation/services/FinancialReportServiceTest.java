package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.FinancialRapport.FinancialReportDto;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

class FinancialReportServiceTest {

    public ReservationService      reservationService;
    public    ChefService             chefService;
    public    IngredientsService      ingredientsService;
    private  FinancialReportService financialReportService;
    private final double A_MOUNT = 5000;
    private final BigDecimal CHEF_EXPENSE = BigDecimal.valueOf(A_MOUNT);
    private final BigDecimal INGREDIENT_EXPENSE = BigDecimal.valueOf(A_MOUNT);

    @BeforeEach
    public void setUp() {
        reservationService = mock(ReservationService.class);
        chefService = mock(ChefService.class);
        ingredientsService = mock(IngredientsService.class);
        financialReportService = new FinancialReportService(reservationService, chefService, ingredientsService);
    }

    @Test
    public void givenAReservationWhereMade_whenGetReport_thenCallGetReservationTotalPrice() {
        when(chefService.getTotalExpense()).thenReturn(CHEF_EXPENSE);
        when(ingredientsService.getTotalExpense()).thenReturn(INGREDIENT_EXPENSE);

        financialReportService.getReport();

        verify(reservationService, times(1)).getReservationTotalPrice();
    }

    @Test
    public void givenAReservationWhereMade_whenGetReport_thenCallChefGetTotalExpense() {
        when(reservationService.getReservationTotalPrice()).thenReturn(A_MOUNT);
        when(chefService.getTotalExpense()).thenReturn(CHEF_EXPENSE);
        when(ingredientsService.getTotalExpense()).thenReturn(INGREDIENT_EXPENSE);

        financialReportService.getReport();

        verify(chefService, times(1)).getTotalExpense();
    }

    @Test
    public void givenAReservationWhereMade_whenGetReport_thenCallIngredientGetTotalExpense() {
        when(reservationService.getReservationTotalPrice()).thenReturn(A_MOUNT);
        when(chefService.getTotalExpense()).thenReturn(CHEF_EXPENSE);
        when(ingredientsService.getTotalExpense()).thenReturn(INGREDIENT_EXPENSE);

        financialReportService.getReport();

        verify(ingredientsService, times(1)).getTotalExpense();
    }

    @Test
    public void givenExpensesButNoIncome_whenGetReport_thenAbsoluteValueOfProfitShouldBeEqualToTheSumOfChefExpenseAndIngredientExpense() {
        when(reservationService.getReservationTotalPrice()).thenReturn(Double.valueOf(0));
        when(chefService.getTotalExpense()).thenReturn(CHEF_EXPENSE);
        when(ingredientsService.getTotalExpense()).thenReturn(INGREDIENT_EXPENSE);

        FinancialReportDto report = financialReportService.getReport();

        assertTrue(report.profits.abs().doubleValue() == CHEF_EXPENSE.add(INGREDIENT_EXPENSE).doubleValue());
    }

    @Test
    public void givenExpensesButNoIncome_whenGetReport_thenProfitShouldNegative() {
        when(reservationService.getReservationTotalPrice()).thenReturn(Double.valueOf(0));
        when(chefService.getTotalExpense()).thenReturn(CHEF_EXPENSE);
        when(ingredientsService.getTotalExpense()).thenReturn(INGREDIENT_EXPENSE);

        FinancialReportDto report = financialReportService.getReport();

        assertTrue(report.profits.doubleValue() < 0);
    }

    @Test
    public void givenIncomeButNoExpense_whenGetReport_thenProfitShouldBeEqualToReservationTotalPrice() {
        double reservationTotalPrice = A_MOUNT;
        when(reservationService.getReservationTotalPrice()).thenReturn(reservationTotalPrice);
        when(chefService.getTotalExpense()).thenReturn(BigDecimal.valueOf(0));
        when(ingredientsService.getTotalExpense()).thenReturn(BigDecimal.valueOf(0));

        FinancialReportDto report = financialReportService.getReport();

        assertTrue(report.profits.doubleValue() == reservationTotalPrice);
    }

    @Test
    public void givenIncomeButNoExpense_whenGetReport_thenProfitShouldBePositive() {
        when(reservationService.getReservationTotalPrice()).thenReturn(A_MOUNT);
        when(chefService.getTotalExpense()).thenReturn(BigDecimal.valueOf(0));
        when(ingredientsService.getTotalExpense()).thenReturn(BigDecimal.valueOf(0));

        FinancialReportDto report = financialReportService.getReport();

        assertTrue(report.profits.doubleValue() > 0);
    }

    @Test
    public void givenAReservationWhereMade_whenGetReport_thenReturnAnInstanceOfFinancialReportDto() {
        when(reservationService.getReservationTotalPrice()).thenReturn(Double.valueOf(0));
        when(chefService.getTotalExpense()).thenReturn(CHEF_EXPENSE);
        when(ingredientsService.getTotalExpense()).thenReturn(INGREDIENT_EXPENSE);


        assertTrue(financialReportService.getReport()instanceof FinancialReportDto);
    }



}
