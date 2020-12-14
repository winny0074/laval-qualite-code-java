package ca.ulaval.glo4002.reservation.domain.money;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MoneyTest {
  private final String SOME_CURRENCY = "CAD";

  @Test
  public void givenInitialAmount_whenAddingWithoutOverflow_thenShouldCombineUnitsAndCents() {
    Money initialAmount = new Money(5, 5, SOME_CURRENCY);
    Money amountToBeAdded = new Money(10, 2, SOME_CURRENCY);
    Money expectedAmount = new Money(15, 7, SOME_CURRENCY);

    Money returnedAmount = initialAmount.add(amountToBeAdded);

    assertTrue(returnedAmount.isEqual(expectedAmount));
  }

  @Test
  public void givenInitialAmount_whenAddingWithCentsOverflow_thenShouldHandleOverflow() {
    Money initialAmount = new Money(0, 99, SOME_CURRENCY);
    Money amountToBeAdded = new Money(0, 2, SOME_CURRENCY);
    Money expectedAmount = new Money(1, 1, SOME_CURRENCY);

    Money returnedAmount = initialAmount.add(amountToBeAdded);

    assertTrue(returnedAmount.isEqual(expectedAmount));
  }

  @Test
  public void givenInitialAmount_whenMultiplyingWithoutOverflow_thenShouldMultiplyUnitsAndCents() {
    Money initialAmount = new Money(5, 5, SOME_CURRENCY);
    Money expectedAmount = new Money(15, 15, SOME_CURRENCY);

    Money returnedAmount = initialAmount.multiply(3);

    assertTrue(returnedAmount.isEqual(expectedAmount));
  }

  @Test
  public void givenNonNullInitialAmount_whenMultiplyingWithCentsOverflow_thenShouldHandleOverflow() {
    Money initialAmount = new Money(3, 66, SOME_CURRENCY);
    Money expectedAmount = new Money(10, 98, SOME_CURRENCY);

    Money returnedAmount = initialAmount.multiply(3);

    assertTrue(returnedAmount.isEqual(expectedAmount));
  }
}
