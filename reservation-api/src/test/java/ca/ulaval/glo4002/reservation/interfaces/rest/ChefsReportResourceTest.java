package ca.ulaval.glo4002.reservation.interfaces.rest;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.chef.GlobalChefsReportDto;
import ca.ulaval.glo4002.reservation.services.ChefService;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

public class ChefsReportResourceTest {

    private  ChefsReportResource chefsReportResource;

    private ChefService chefService;

    @BeforeEach
    public void setup(){
        chefService = mock(ChefService.class);
        chefsReportResource = new ChefsReportResource(chefService);
    }

    @Test
    public void whenGetChefsReport_thenReturnAcceptedAnswer(){

        GlobalChefsReportDto globalChefsReportDto = mock(GlobalChefsReportDto.class);
        willReturn(globalChefsReportDto).given(chefService).getGlobalChefReport();

        Response response = chefsReportResource.getReportChefs();

        assertEquals(HttpStatus.OK_200, response.getStatus());
    }
}
