package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

class FinancialReportServiceTest {

    public ReservationService      reservationService;
    public    ChefService             chefService;
    public    IngredientsService      ingredientsService;
    private  FinancialReportService financialReportService;
    private  FinancialReportService financialReportServiceSpy;
    private final GloDateTime START_DATE = GloDateTime.ofLocalDateFormat("2050-07-20");
    private final GloDateTime END_DATE =  GloDateTime.ofLocalDateFormat("2050-07-20");
    private final BigDecimal INCOME = BigDecimal.valueOf(5000);
    private final BigDecimal CHEF_EXPENSE = BigDecimal.valueOf(5000);
    private final BigDecimal INGREDIENT_EXPENSE = BigDecimal.valueOf(5000);

    @BeforeEach
    public void setUp() {
        reservationService = mock(ReservationService.class);
        chefService = mock(ChefService.class);
        ingredientsService = mock(IngredientsService.class);
        financialReportService = new FinancialReportService(reservationService, chefService, ingredientsService);
        financialReportServiceSpy = spy(financialReportService);

    }

    @Test
    public void givenAReservationWhereMade_whenGetReport_thenCallGetReservationTotalPrice() {
        when(chefService.getTotalExpense()).thenReturn(CHEF_EXPENSE);
        when(ingredientsService.getTotalExpense()).thenReturn(INGREDIENT_EXPENSE);



        financialReportService.getReport();

        verify(reservationService, times(1)).getReservationTotalPrice();
    }


}
