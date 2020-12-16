package ca.ulaval.glo4002.reservation.interfaces.rest;

import ca.ulaval.glo4002.reservation.services.ChefService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reports")
public class ChefsReportResource {

    private ChefService chefService ;

    @Inject
    public ChefsReportResource(ChefService chefService) {
        this.chefService = chefService;
    }


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/chefs")
  public Response getReportChefs() {
      return Response.ok(chefService.getGlobalChefReport()).build();
    }
}
