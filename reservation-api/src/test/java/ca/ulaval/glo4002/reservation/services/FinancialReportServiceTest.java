package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.restaurant.RestaurantConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

class FinancialReportServiceTest {

    public ReservationService      reservationService;
    public    ChefService             chefService;
    public    IngredientsService      ingredientsService;
    private   RestaurantConfiguration restaurantConfiguration;
    private  FinancialReportService financialReportService;
    private  FinancialReportService financialReportServiceSpy;
    private final GloDateTime START_DATE = GloDateTime.ofLocalDateFormat("2050-07-20");
    private final GloDateTime END_DATE =  GloDateTime.ofLocalDateFormat("2050-07-20");
    private final BigDecimal INCOME = BigDecimal.valueOf(5000);

    @BeforeEach
    public void setUp() {
        reservationService = mock(ReservationService.class);
        chefService = mock(ChefService.class);
        ingredientsService = mock(IngredientsService.class);
        restaurantConfiguration = mock(RestaurantConfiguration.class);
        financialReportService = new FinancialReportService(reservationService, chefService,
                                                            ingredientsService, restaurantConfiguration);
        financialReportServiceSpy = spy(financialReportService);

        when(restaurantConfiguration.getEventPeriodStartDate()).thenReturn(START_DATE);
        when(restaurantConfiguration.getEventPeriodEndDate()).thenReturn(END_DATE);
    }

    @Test
    public void givenAReservationWhereMade_whenGetReport_thenCallGetReservationTotalPrice() {
        doNothing().when(chefService).getTotalExpense();
        doNothing().when(ingredientsService).getIngredientsToOrderInTotalFormat(START_DATE.toPresentationDateFormat(),
                                                                                END_DATE.toPresentationDateFormat());

        financialReportService.getReport();

        verify(reservationService, times(1)).getReservationTotalPrice();
    }


}
