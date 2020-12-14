package ca.ulaval.glo4002.reservation.interfaces.rest.validator;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.country.CountryDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.customer.CustomerDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ReservationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.table.TableDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.reservationException.InvalidFormat;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.reservationException.InvalidReservationQuantity;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.ReservationBodyValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ReservationRequestBodyValidatorTest {
  private final String SOME_CUSTOMER_NAME = "Alice";
  private final String[] SOME_RESTRICTIONS = new String[] {};
  private final String SOME_COUNTRY_CODE = "A";
  private final String SOME_COUNTRY_FULLNAME = "B";
  private final String SOME_COUNTRY_CURRENCY = "C";
  private final String SOME_RESERVATION_DATE = "_";
  private final String SOME_VENDOR_CODE = "*";
  private final String SOME_DINNER_DATE = "+";
  private final String EMPTY_STRING = "";
  private final String AN_INVALID_RESTRICTION = "vegatarian";
  private ReservationBodyValidator reservationBodyValidator;
  private ReservationDto reservationDto;

  @BeforeEach
  public void initialize() throws JsonProcessingException {
    reservationBodyValidator = new ReservationBodyValidator();
    reservationDto = makeValidReservationDto();
  }

  @Test
  public void givenValidStringType_whenValidatingStringType_thenThrowsNothing() {
    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    assertDoesNotThrow(tryToValidateStringType);
  }

  @Test
  public void givenMissingVendorCode_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.vendorCode = EMPTY_STRING;

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidFormat.class, tryToValidateStringType);
  }

  @Test
  public void givenMissingDinnerDate_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.dinnerDate = EMPTY_STRING;

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidFormat.class, tryToValidateStringType);
  }

  @Test
  public void givenMissingCountryCode_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.country.code = EMPTY_STRING;

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidFormat.class, tryToValidateStringType);
  }

  @Test
  public void givenMissingCountryFullname_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.country.fullname = EMPTY_STRING;

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidFormat.class, tryToValidateStringType);
  }

  @Test
  public void givenMissingCountryCurrency_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.country.currency = EMPTY_STRING;

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidFormat.class, tryToValidateStringType);
  }

  @Test
  public void givenMissingReservationDate_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.creationDate = EMPTY_STRING;

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidFormat.class, tryToValidateStringType);
  }

  @Test
  public void givenMissingTable_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.tableDtos = null;

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidFormat.class, tryToValidateStringType);
  }

  @Test
  public void givenMissingCustomers_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.tableDtos.get(0).customers = null;

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidFormat.class, tryToValidateStringType);
  }

  @Test
  public void givenEmptyTableList_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.tableDtos.clear();

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidReservationQuantity.class, tryToValidateStringType);
  }

  @Test
  public void givenEmptyCustomerList_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.tableDtos.get(0).customers.clear();

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidReservationQuantity.class, tryToValidateStringType);
  }

  @Test
  public void givenInvalidStringRestrictionName_whenValidatingStringType_thenThrowsInvalidFormat() {
    reservationDto.tableDtos.get(0).customers.get(0).restrictions =
        new String[] {AN_INVALID_RESTRICTION};

    Executable tryToValidateStringType =
        () -> {
          reservationBodyValidator.validateReservationBody(reservationDto);
        };

    Assertions.assertThrows(InvalidFormat.class, tryToValidateStringType);
  }

  private ReservationDto makeValidReservationDto() throws JsonProcessingException {
    ArrayList<TableDto> tableDtos = new ArrayList<>();
    tableDtos.add(makeValidTableDto());
    return new ReservationDto(SOME_VENDOR_CODE, SOME_DINNER_DATE, tableDtos, makeValidFromField());
  }

  private TableDto makeValidTableDto() {
    ArrayList<CustomerDto> customers = new ArrayList<>();
    CustomerDto customer = makeValidCustomerDto();
    customers.add(customer);
    return new TableDto(customers);
  }

  private CustomerDto makeValidCustomerDto() {
    return new CustomerDto(SOME_CUSTOMER_NAME, SOME_RESTRICTIONS);
  }

  private Map<String, Object> makeValidFromField() {
    CountryDto country = makeValidCountryDto();

    Map<String, Object> from = new HashMap<>();
    from.put("country", country);
    from.put("reservationDate", SOME_RESERVATION_DATE);

    return from;
  }

  private CountryDto makeValidCountryDto() {
    return new CountryDto(SOME_COUNTRY_CODE, SOME_COUNTRY_FULLNAME, SOME_COUNTRY_CURRENCY);
  }
}
