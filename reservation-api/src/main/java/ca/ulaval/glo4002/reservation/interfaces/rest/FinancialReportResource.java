package ca.ulaval.glo4002.reservation.interfaces.rest;

import ca.ulaval.glo4002.reservation.services.FinancialReportService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reports")
public class FinancialReportResource {

    private final FinancialReportService financialReportService ;

    public FinancialReportResource() {
        financialReportService = new FinancialReportService();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/total")
    public Response getFinancialReport() {
        return Response.ok(financialReportService.getReport()).build();
    }

}
