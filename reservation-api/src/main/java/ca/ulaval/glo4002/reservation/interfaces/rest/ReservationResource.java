package ca.ulaval.glo4002.reservation.interfaces.rest;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ReservationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ResponseReservationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.ReservationBodyValidator;
import ca.ulaval.glo4002.reservation.services.ReservationService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/reservations")
public class ReservationResource {
  private final ReservationService reservationService;
  private final ReservationBodyValidator reservationBodyValidator;

  public ReservationResource() {
    this.reservationBodyValidator = new ReservationBodyValidator();
    this.reservationService = new ReservationService();
  }

  public ReservationResource(
      ReservationBodyValidator reservationBodyValidator,
      ReservationService reservationService) {
    this.reservationBodyValidator = reservationBodyValidator;
    this.reservationService = reservationService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postReservation(ReservationDto reservation) {
    reservationBodyValidator.validateReservationBody(reservation);

    String reservationNumber = reservationService.create(reservation);

    return Response.ok()
        .status(Response.Status.CREATED)
        .header("Location", "/reservations/" + reservationNumber)
        .build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{reservationNumber}")
  public Response getReservation(@PathParam("reservationNumber") String reservationNumber) {
    ResponseReservationDto responseReservationDto = reservationService.findById(reservationNumber);
    return Response.ok(responseReservationDto).build();
  }
}
