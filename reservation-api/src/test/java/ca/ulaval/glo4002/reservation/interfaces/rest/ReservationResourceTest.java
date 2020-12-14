package ca.ulaval.glo4002.reservation.interfaces.rest;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ResponseReservationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.ReservationBodyValidator;
import ca.ulaval.glo4002.reservation.services.ReservationService;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

class ReservationResourceTest {
  private static final String RESERVATION_NUMBER = "TEAM-56700";
  private ReservationResource reservationResource;
  private ReservationService reservationService;
  private ReservationBodyValidator reservationBodyValidator;

  @BeforeEach
  public void setUp() {
    reservationBodyValidator = mock(ReservationBodyValidator.class);
    reservationService = mock(ReservationService.class);
    reservationResource = new ReservationResource(reservationBodyValidator, reservationService);
  }

  @Test
  public void whenGetReservationDoesNotThrow_shouldReturnAcceptedAnswer() {
    ResponseReservationDto responseReservationDto = mock(ResponseReservationDto.class);
    willReturn(responseReservationDto).given(reservationService).findById(RESERVATION_NUMBER);

    Response response = reservationResource.getReservation(RESERVATION_NUMBER);

    assertEquals(HttpStatus.OK_200, response.getStatus());
  }
}
