package ca.ulaval.glo4002.reservation.domain.reservation;

import ca.ulaval.glo4002.reservation.domain.customer.CustomerRequest;
import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.country.Country;
import ca.ulaval.glo4002.reservation.domain.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientList;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import ca.ulaval.glo4002.reservation.domain.table.Table;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Reservation {
  private ReservationNumber identificationNumber;
  private String            vendorCode;
  private GloDateTime       dinnerDate;
  private Collection<Table> tables;
  private Country           country;
  private GloDateTime       creationDate;

  private Reservation(String vendorCode, GloDateTime dinnerDate, GloDateTime reservationDate,
                      Country country, Collection<Table> tables, ReservationNumber identificationNumber) {
    this.vendorCode = vendorCode;
    this.dinnerDate = dinnerDate;
    this.creationDate = reservationDate;
    this.country = country;
    this.tables = tables;
    this.identificationNumber = identificationNumber;
  }

  public static Reservation from(ReservationRequest request, IngredientList availableIngredients,
                                 ReservationNumber identificationNumber) {
    Country country = new Country(request.countryCode, request.countryName, request.countryCurrency);
    Collection<Table> tables = makeTablesFrom(request.tables, availableIngredients);
    Reservation reservation = new Reservation(request.vendorCode,
                                              request.dinnerDate,
                                              request.reservationDate,
                                              country, tables, identificationNumber);
    //reservation.setIdentificationNumber(reservationNumberGenerator.getNextReservationNumber());
    return reservation;
  }

  private static Collection<Table> makeTablesFrom(Collection<Collection<CustomerRequest>> tableRequests,
                                                  IngredientList availableIngredients) {
    Collection<Table> tables = new LinkedList<>();
    for (Collection<CustomerRequest> tableRequest : tableRequests) {
      List<Customer> customersAtTable = new LinkedList<>();
      for (CustomerRequest customerRequest : tableRequest) {
        List<Restriction> restrictions = customerRequest.restrictions
                                                        .stream()
                                                        .map(restrictionName -> Restriction.fromName(restrictionName))
                                                        .collect(Collectors.toList());
        customersAtTable.add(new Customer(customerRequest.name, restrictions, availableIngredients));
      }
      tables.add(new Table(customersAtTable));
    }
    return tables;
  }

  public ReservationNumber getIdentificationNumber() {
    return identificationNumber;
  }


  public GloDateTime getDinnerDate() {
    return dinnerDate;
  }

  public Money getPrice() {
    Money price = new Money(0, 0, country.getCurrency());

    for (Customer customer : getCustomers()) {
      price = price.add(customer.getPrice());
    }

    return price;
  }

  public List<Customer> getCustomers() {
    return tables.stream()
                 .flatMap(table -> table.getCustomers().stream())
                 .collect(Collectors.toList());
  }

  public List<Restriction> getAllRestrictions() {
    return tables.stream()
                  .map(Table::getAllRestrictions)
                  .flatMap(Collection::stream)
                  .distinct()
                  .collect(Collectors.toList());
  }

  public int getNumberOfCustomers() {
    return getCustomers().size();
  }

  public boolean containsAllergicCustomer() {
    return getCustomers().stream().anyMatch(Customer::hasAllergy);
  }

  public boolean containsAllergen() {
    return getIngredients().containsAllergen();
  }

  public IngredientList getIngredients() {
    return new IngredientList(tables.stream().flatMap(table -> table.getIngredients().stream()).collect(Collectors.toList()));
  }

  public GloDateTime getReservationDate() {
    return creationDate;
  }
}
