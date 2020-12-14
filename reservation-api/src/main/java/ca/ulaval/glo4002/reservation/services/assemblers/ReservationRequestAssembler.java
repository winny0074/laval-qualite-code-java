package ca.ulaval.glo4002.reservation.services.assemblers;

import ca.ulaval.glo4002.reservation.domain.customer.CustomerRequest;
import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationRequest;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.customer.CustomerDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.reservation.ReservationDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.table.TableDto;

import java.util.LinkedList;
import java.util.List;

public class ReservationRequestAssembler {
  public ReservationRequest from(ReservationDto dto) {
    ReservationRequest request = new ReservationRequest();
    request.vendorCode = dto.vendorCode;
    request.reservationDate = GloDateTime.ofIsoDateTimeFormat(dto.creationDate);
    request.dinnerDate = GloDateTime.ofIsoDateTimeFormat(dto.dinnerDate);
    request.countryCode = dto.country.code;
    request.countryName = dto.country.fullname;
    request.countryCurrency = dto.country.currency;
    request.tables = new LinkedList<>();
    for (TableDto tableDto: dto.tableDtos) {
      List<CustomerRequest> newTable = new LinkedList<>();
      for (CustomerDto customerDto : tableDto.customers) {
        CustomerRequest newCustomerRequest = new CustomerRequest();
        newCustomerRequest.name = customerDto.name;
        newCustomerRequest.restrictions = List.of(customerDto.restrictions);
        newTable.add(newCustomerRequest);
      }
      request.tables.add(newTable);
    }
    return request;
  }
}
