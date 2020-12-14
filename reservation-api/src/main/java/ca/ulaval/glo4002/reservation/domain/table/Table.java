package ca.ulaval.glo4002.reservation.domain.table;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.InvalidPeopleAmount;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Table {
  private static final int MAXIMUM_PEOPLE_PER_TABLE = 4;
  private List<Customer> customers;

  public Table(List<Customer> customers) {
    this.customers = customers;
    validatePeopleAmount();
  }

  private void validatePeopleAmount() {
    if (customers.size() > MAXIMUM_PEOPLE_PER_TABLE) throw new InvalidPeopleAmount();
  }

  public List<Customer> getCustomers() {
    return this.customers;
  }

  public Collection<Ingredient> getIngredients() {
    return customers.stream().flatMap(customer -> customer.getIngredients().stream()).collect(Collectors.toList());
  }

  public Set<Restriction> getAllRestrictions() {
      return customers.stream()
                .map(customer -> {
                  if(customer.getRestrictions().isEmpty()) {
                    return List.of(Restriction.AUCUNE);
                  }
                  return customer.getRestrictions();
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
  }
}
