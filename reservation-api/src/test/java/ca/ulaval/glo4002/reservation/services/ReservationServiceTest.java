package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.exception.reservationException.AllergyException;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.InvalidPeopleAmount;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.InvalidReservationDate;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.ReservationNotFound;
import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationMother;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationRequest;
import ca.ulaval.glo4002.reservation.domain.restaurant.Restaurant;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ReservationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.ReservationDtoMother;
import ca.ulaval.glo4002.reservation.services.assemblers.ReservationDtoAssembler;
import ca.ulaval.glo4002.reservation.services.assemblers.ReservationRequestAssembler;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.mockito.Mockito.*;

class ReservationServiceTest {
  private final String                        A_RESERVATION_ID = "TEAM-123";
  private final String                        NOT_EXISTING_RESERVATION_ID = "TEAM-123";
  private       ReservationService          reservationService;
  private       Restaurant                  restaurant;
  private       ReservationRequestAssembler reservationRequestAssembler;
  private       ReservationDtoAssembler     reservationDtoAssembler;
  private       ReservationRequest          reservationRequest;
  private       Reservation                 reservation;

  @BeforeEach
  public void setUpReservationService() {
    reservationRequest = mock(ReservationRequest.class);
    reservationDtoAssembler = mock(ReservationDtoAssembler.class);
    reservationRequestAssembler = mock(ReservationRequestAssembler.class);
    reservation = mock(Reservation.class);
    restaurant = mock(Restaurant.class);

    reservationService = new ReservationService(restaurant,
                                                reservationRequestAssembler,
                                                reservationDtoAssembler);
  }

  @Test
  public void givenId_whenFindById_thenCallRestaurantFindReservationById() {
    reservationService.findById(A_RESERVATION_ID);

    verify(restaurant).findReservationById(A_RESERVATION_ID);
  }

  @Test
  public void givenId_whenFindById_thenCallReservationDtoAssemblerFrom() {
    Reservation basicReservation = ReservationMother.createBasicReservation();
    when(restaurant.findReservationById(A_RESERVATION_ID)).thenReturn(basicReservation);

    reservationService.findById(A_RESERVATION_ID);

    verify(reservationDtoAssembler).from(basicReservation);
  }

  @Test
  public void givenAnInvalidId_whenFindById_thenThrowReservationNotFoundException() {
    when(restaurant.findReservationById(NOT_EXISTING_RESERVATION_ID)).thenThrow(new ReservationNotFound(NOT_EXISTING_RESERVATION_ID));

    Executable findReservation = () -> reservationService.findById(NOT_EXISTING_RESERVATION_ID);

    assertThrows(ReservationNotFound.class, findReservation);
  }

  @Test
  public void givenAnyReservationDto_whenCreatingAReservation_thenCallRestaurantReserve() {
    ReservationDto basicReservationDto = ReservationDtoMother.createBasicReservationDto();

    when(reservationRequestAssembler.from(basicReservationDto)).thenReturn(reservationRequest);

    reservationService.create(basicReservationDto);

    verify(restaurant).reserve(reservationRequest);
  }

  @Test
  public void givenAnyReservationDto_whenCreatingAReservation_thenCallReservationRequestAssemblerFrom() {
    ReservationDto basicReservationDto = ReservationDtoMother.createBasicReservationDto();
    reservationService.create(basicReservationDto);

    verify(reservationRequestAssembler).from(basicReservationDto);
  }

  @Test
  public void givenReservationWitSocialDistancingException_whenFindById_thenThrowInvalidPeopleAmountException() {
    ReservationDto basicReservationDto = ReservationDtoMother.createBasicReservationDto();
    when(restaurant.reserve(reservationRequest)).thenThrow(InvalidPeopleAmount.class);
    when(reservationRequestAssembler.from(basicReservationDto)).thenReturn(reservationRequest);

    Executable executable = () -> reservationService.create(basicReservationDto);

    assertThrows(InvalidPeopleAmount.class, executable);
  }

  @Test
  public void givenReservationWithReservationDateException_whenFindById_thenThrowInvalidReservationDateException() {
    ReservationDto basicReservationDto = ReservationDtoMother.createBasicReservationDto();
    when(restaurant.reserve(reservationRequest)).thenThrow(InvalidReservationDate.class);
    when(reservationRequestAssembler.from(basicReservationDto)).thenReturn(reservationRequest);

    Executable executable = () -> reservationService.create(basicReservationDto);

    assertThrows(InvalidReservationDate.class, executable);
  }

  @Test
  public void givenReservationWithAllergyException_whenFindById_thenThrowAllergyException() {
    ReservationDto basicReservationDto = ReservationDtoMother.createBasicReservationDto();
    when(restaurant.reserve(reservationRequest)).thenThrow(AllergyException.class);
    when(reservationRequestAssembler.from(basicReservationDto)).thenReturn(reservationRequest);

    Executable executable = () -> reservationService.create(basicReservationDto);

    assertThrows(AllergyException.class, executable);
  }

//  @Test
//  public void given_when_then() {
//    ReservationService reservationServiceSpy = spy(reservationService);
//    doReturn().when(reservationServiceSpy).findAll();
//
//    reservationService.getReservationTotalPrice();
//  }
}
