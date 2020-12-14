package ca.ulaval.glo4002.reservation.interfaces.rest.validators;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.customer.CustomerDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ReservationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.table.TableDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.InterfacesException;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.reservationException.InvalidFormat;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.interfacesException.reservationException.InvalidReservationQuantity;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.common.StringValidator;

import java.util.Objects;

public class ReservationBodyValidator {
  public ReservationBodyValidator() {}

  public void validateReservationBody(ReservationDto reservationDto) throws InterfacesException {

    validateExtraFields(reservationDto.hasExtraFields);
    validateVendorCode(reservationDto.vendorCode);
    validateDinnerDate(reservationDto.dinnerDate);
    validateCountry(reservationDto);
    validateCountryCode(reservationDto.country.code);
    validateCountryFullName(reservationDto.country.fullname);
    validateCountryCurrency(reservationDto.country.currency);
    validateReservationDate(reservationDto.creationDate);
    validateCustomersName(reservationDto);
    validateRestrictionsNames(reservationDto);
    validateCustomers(reservationDto);
    validateTable(reservationDto);
  }

  private void validateExtraFields(boolean hasExtraFields) {
    if (hasExtraFields) {
      throw new InvalidFormat();
    }
  }

  private void validateVendorCode(String vendorCode) {
    StringValidator.isNullOrEmpty(vendorCode, new InvalidFormat());
  }

  private void validateDinnerDate(String dinnerDate) {
    StringValidator.isNullOrEmpty(dinnerDate, new InvalidFormat());
  }

  private void validateCountry(ReservationDto reservationDto) {
    if (Objects.isNull(reservationDto.country) || reservationDto.country.hasExtraFields) {
      throw new InvalidFormat();
    }
  }

  private void validateCountryCode(String countryCode) {
    StringValidator.isNullOrEmpty(countryCode, new InvalidFormat());
  }

  private void validateCountryFullName(String countryFullname) {
    StringValidator.isNullOrEmpty(countryFullname, new InvalidFormat());
  }

  private void validateCountryCurrency(String countryCurrency) {
    StringValidator.isNullOrEmpty(countryCurrency, new InvalidFormat());
  }

  private void validateReservationDate(String reservationDate) {
    StringValidator.isNullOrEmpty(reservationDate, new InvalidFormat());
  }

  private void validateCustomersName(ReservationDto reservationDto) {
    if (Objects.isNull(reservationDto.tableDtos)) {
      throw new InvalidFormat();
    }
    if (reservationDto.tableDtos.size() > 0) {
      for (TableDto tableDto : reservationDto.tableDtos) {
        if (Objects.isNull(tableDto.customers)) {
          throw new InvalidFormat();
        }

        if (tableDto.customers.size() > 0) {
          for (CustomerDto customerDto : tableDto.customers) {
            StringValidator.isNullOrEmpty(customerDto.name, new InvalidFormat());
          }
        } else {
          throw new InvalidReservationQuantity();
        }
      }
    }
  }

  private void validateRestrictionsNames(ReservationDto reservationDto) {
    if (Objects.isNull(reservationDto.tableDtos)) {
      throw new InvalidFormat();
    }

    if (reservationDto.tableDtos.size() > 0) {
      for (TableDto tableDto : reservationDto.tableDtos) {
        if (!Objects.isNull(tableDto.customers) && tableDto.customers.size() > 0) {
          for (CustomerDto customerDto : tableDto.customers) {
            if (!Objects.isNull(customerDto.restrictions) && customerDto.restrictions.length > 0) {
              for (int i = 0; i < customerDto.restrictions.length; i++) {
                if (!Restriction.getRestrictionNames().contains(customerDto.restrictions[i])) {
                  throw new InvalidFormat();
                }
              }
            } else if (Objects.isNull(customerDto.restrictions)) {
              throw new InvalidFormat();
            }
          }
        } else {
          throw new InvalidReservationQuantity();
        }
      }
    }
  }

  private void validateCustomers(ReservationDto reservationDto) {
    for (TableDto tableDto : reservationDto.tableDtos) {
      if (tableDto.customers.isEmpty()) {
        throw new InvalidFormat();
      }
      for (CustomerDto customerDto : tableDto.customers) {
        if (customerDto.hasExtraFields) {
          throw new InvalidFormat();
        }
      }
    }
  }

  private void validateTable(ReservationDto reservationDto) {
    if (reservationDto.tableDtos.isEmpty()) {
      throw new InvalidReservationQuantity();
    }
    for (TableDto tableDto : reservationDto.tableDtos) {
      if (tableDto.hasExtraFields) {
        throw new InvalidFormat();
      }
    }
  }
}
