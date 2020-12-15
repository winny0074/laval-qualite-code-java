package ca.ulaval.glo4002.reservation.domain.reservation;

import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.ReservationNotFound;
import ca.ulaval.glo4002.reservation.infrastructure.ReservationPersistenceInMemory;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

class ReservationPersistenceInMemoryTest {
  private static final Integer CUSTOMERS_PER_RESERVATION = 2;
  private static final Integer NUMBER_OF_RESERVATIONS_SUPERIOR_TO_ONE = 3;
  private final GloDateTime VALID_DINNER_DATE = GloDateTime.ofIsoDateTimeFormat("2150-07-20T00:00:00.000Z");
  private final String VENDOR_PREFIX = "TEAM";
  private final ReservationNumber PRESET_IDENTIFICATION_NUMBER = ReservationNumber.create(VENDOR_PREFIX, 1l);
  private final ReservationNumber OTHER_IDENTIFICATION_NUMBER = ReservationNumber.create(VENDOR_PREFIX, 2l);
  private ReservationPersistenceInMemory repository = new ReservationPersistenceInMemory();
  private Reservation reservationInRepository;

  @BeforeEach
  public void initializeRepository() {
    repository.clear();

    reservationInRepository = makeAndSaveNewReservationWithId(PRESET_IDENTIFICATION_NUMBER);
    when(reservationInRepository.getDinnerDate()).thenReturn(VALID_DINNER_DATE);
    when(reservationInRepository.getNumberOfCustomers()).thenReturn(CUSTOMERS_PER_RESERVATION);
  }

  @Test
  public void givenRemovedReservation_whenTryingToFindById_shouldThrow() {
    repository.remove(reservationInRepository);

    Executable tryToFindById =
        () -> {
          repository.findById(reservationInRepository.getIdentificationNumber());
        };

    assertThrows(ReservationNotFound.class, tryToFindById);
  }

  @Test
  public void givenReservation_whenFindById_shouldReturnSameReservation() {
    Reservation returnedReservation = repository.findById(reservationInRepository.getIdentificationNumber());

    assertEquals(returnedReservation, reservationInRepository);
  }

  @Test
  public void givenReservations_whenFindAll_shouldReturnAll() {
    Reservation firstReservation = makeAndSaveNewReservationWithId(PRESET_IDENTIFICATION_NUMBER);
    Reservation secondReservation = makeAndSaveNewReservationWithId(OTHER_IDENTIFICATION_NUMBER);
    repository.save(firstReservation);
    repository.save(secondReservation);

    Collection<Reservation> reservations = repository.findAll();

    assertTrue(reservations.contains(firstReservation));
    assertTrue(reservations.contains(secondReservation));
  }

  @Test
  public void givenReservation_whenSavingReservationWithSameId_shouldOverwrite() {
    Reservation firstReservation = makeAndSaveNewReservationWithId(PRESET_IDENTIFICATION_NUMBER);
    repository.save(firstReservation);

    Reservation secondReservation = makeAndSaveNewReservationWithId(PRESET_IDENTIFICATION_NUMBER);
    repository.save(secondReservation);
    Reservation returnedReservation = repository.findById(PRESET_IDENTIFICATION_NUMBER);

    assertNotEquals(returnedReservation, firstReservation);
    assertEquals(returnedReservation, secondReservation);
  }

  @Test
  public void givenNoReservations_whenGetDailyOccupancy_shouldReturnZero() {
    repository.clear();

    long dailyOccupancy = repository.getDailyOccupancy(VALID_DINNER_DATE);

    assertEquals(0L, dailyOccupancy);
  }

  @Test
  public void givenManyReservationsInRepository_whenGetDailyOccupancy_shouldReturnCorrectAmount() {
    addMultipleSameDayReservationsToRepository(NUMBER_OF_RESERVATIONS_SUPERIOR_TO_ONE);

    long dailyOccupancy = repository.getDailyOccupancy(VALID_DINNER_DATE);

    assertEquals(CUSTOMERS_PER_RESERVATION * NUMBER_OF_RESERVATIONS_SUPERIOR_TO_ONE, dailyOccupancy);
  }

  private Reservation makeAndSaveNewReservationWithId(ReservationNumber preset_identification_number) {
    Reservation mockReservation = mock(Reservation.class);
    when(mockReservation.getIdentificationNumber()).thenReturn(preset_identification_number);
    repository.save(mockReservation);
    return mockReservation;
  }

  private void addMultipleSameDayReservationsToRepository(int numberOfReservations) {
    repository.clear();
    for (int i = 0; i < numberOfReservations; i++) {
      when(reservationInRepository.getIdentificationNumber()).thenReturn(ReservationNumber.create("", Long.valueOf(i)));
      repository.save(reservationInRepository);
    }
  }
}
