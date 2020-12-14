package ca.ulaval.glo4002.reservation.domain.table;

import ca.ulaval.glo4002.reservation.domain.customer.Customer;
import ca.ulaval.glo4002.reservation.domain.customer.CustomerMother;
import ca.ulaval.glo4002.reservation.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.InvalidPeopleAmount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TableTest {

  @Test
  public void givenFourCustomers_whenNewTable_thenDoesNotThrow() {
    List<Customer> fourCustomers = createCustomers(4);

    Executable makeNewTable =
        () -> {
          new Table(fourCustomers);
        };

    assertDoesNotThrow(makeNewTable);
  }

  @Test
  public void givenFiveCustomers_whenNewTable_thenThrowInvalidPeopleAmount() {
    List<Customer> fiveCustomers = createCustomers(5);

    Executable makeNewTable =
        () -> {
          new Table(fiveCustomers);
        };

    assertThrows(InvalidPeopleAmount.class, makeNewTable);
  }

  @Test
  public void givenMultipleCustomers_whenGetIngredients_shouldGetExactlyAllIngredientsFromAllCustomers() {
    Table table = tableWithMultipleCustomers();

    Collection<Ingredient> returnedIngredients = table.getIngredients();
    Collection<Ingredient> expectedIngredients = table.getCustomers().stream()
                                                                     .flatMap(customer -> customer.getIngredients().stream())
                                                                     .collect(Collectors.toList());

    Predicate<Ingredient> predicate = ingredient -> expectedIngredients.stream()
                                                                        .anyMatch(ingredient1 -> ingredient1.isEquals(ingredient));
    assertTrue(returnedIngredients.stream().allMatch(predicate));
  }

  private Table tableWithMultipleCustomers() {
    return new Table(Arrays.asList(CustomerMother.createCustomerWithRestrictions(),
                                   CustomerMother.createCustomerWithoutRestrictions()));
  }

  private List<Customer> createCustomers(int numberOfCustomers) {
    Customer customerDummy = mock(Customer.class);
    List<Customer> customers = new LinkedList<>();
    for (int j = 0; j < numberOfCustomers; j++) {
      customers.add(customerDummy);
    }
    return customers;
  }
}
