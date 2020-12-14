package ca.ulaval.glo4002.reservation.services.assemblers;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.customer.Customer;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.customer.CustomerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDtoAssembler {

  public List<CustomerDto> from(List<Customer> customers) {
    List<CustomerDto> customerDtos = new ArrayList<>();
    for (Customer customer : customers) {
      List<String> restrictionNames =
          customer.getRestrictions().stream()
              .map(Restriction::toString)
              .collect(Collectors.toUnmodifiableList());
      String[] restrictions = restrictionNames.toArray(new String[0]);
      CustomerDto customerDto = new CustomerDto(customer.getName(), restrictions);
      customerDtos.add(customerDto);
    }
    return customerDtos;
  }
}
