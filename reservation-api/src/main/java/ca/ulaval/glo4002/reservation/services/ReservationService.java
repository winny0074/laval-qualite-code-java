package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.reservation.Reservation;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationIdentifierGenerator;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationRequest;
import ca.ulaval.glo4002.reservation.domain.restaurant.Restaurant;
import ca.ulaval.glo4002.reservation.infrastructure.ReservationPersistenceInMemory;
import ca.ulaval.glo4002.reservation.infrastructure.RestaurantContextPersistenceInMemory;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ReservationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ResponseReservationDto;
import ca.ulaval.glo4002.reservation.domain.exception.DomainException;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.ReservationNotFound;
import ca.ulaval.glo4002.reservation.infrastructure.supplier.ExternalIngredientClient;
import ca.ulaval.glo4002.reservation.services.assemblers.ReservationRequestAssembler;
import ca.ulaval.glo4002.reservation.services.assemblers.ReservationDtoAssembler;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
  private final Restaurant restaurant;
  private final ReservationRequestAssembler reservationRequestAssembler;
  private final ReservationDtoAssembler reservationDtoAssembler;

  public ReservationService() {
    this.reservationRequestAssembler= new ReservationRequestAssembler();
    this.reservationDtoAssembler = new ReservationDtoAssembler();
    this.restaurant = new Restaurant(RestaurantContextPersistenceInMemory.getInstance(),
                                     new ExternalIngredientClient(),
                                     ReservationPersistenceInMemory.getInstance(),
                                     new ChefService(),
                                     ReservationIdentifierGenerator.getInstance());
  }

  public ReservationService(Restaurant restaurant,
                            ReservationRequestAssembler reservationRequestAssembler,
                            ReservationDtoAssembler reservationDtoAssembler) {
    this.restaurant = restaurant;
    this.reservationRequestAssembler = reservationRequestAssembler;
    this.reservationDtoAssembler = reservationDtoAssembler;
  }

  public ResponseReservationDto findById(String id) throws ReservationNotFound {
    Reservation reservation = restaurant.findReservationById(id);
    return reservationDtoAssembler.from(reservation);
  }

  public String create(ReservationDto reservationDto) throws DomainException {
    ReservationRequest reservationRequest = reservationRequestAssembler.from(reservationDto);
    return restaurant.reserve(reservationRequest);
  }

  public List<ResponseReservationDto> findAll() throws DomainException {
    List<Reservation> reservations = restaurant.findAll();
    return reservationDtoAssembler.from(reservations);
  }

  public double getReservationTotalPrice() throws DomainException {
    return findAll().stream()
                    .map(reservation -> reservation.reservationPrice)
                    .reduce(BigDecimal.valueOf(0d), BigDecimal::add)
                    .doubleValue();
  }
}
