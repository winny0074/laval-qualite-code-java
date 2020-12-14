package ca.ulaval.glo4002.reservation.domain.money;

import java.math.BigDecimal;

public final class Money {
  private final int units;
  private final int cents;
  private final String currency;

  public Money(Money money) {
    this.units = money.getUnits();
    this.cents = money.getCents();
    this.currency = money.getCurrency();
  }

  public Money(int units, int cents, String currency) {
    this.units = units;
    this.cents = cents;
    this.currency = currency;
  }

  public Money(double value, String currency) {
    this.units = (int) Math.floor(value);
    this.cents =  (int) Math.round((value - (int) Math.floor(value)) * 100);
    this.currency = currency;
  }

  public int getUnits() {
    return units;
  }

  public int getCents() {
    return cents;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal toBigDecimal() {
    return new BigDecimal(units + ((double) cents / 100));
  }

  public boolean isEqual(Money money) {
    return units == money.getUnits() && cents == money.getCents() && currency.equals(money.getCurrency());
  }

  public Money add(Money money) {
    int newCents = cents + money.getCents();
    int newUnits = units + money.getUnits();
    int unitsFromCentsOverflow = 0;

    if (newCents >= 100) {
      unitsFromCentsOverflow = Math.floorDiv(newCents, 100);
      newCents -= unitsFromCentsOverflow * 100;
      newUnits += unitsFromCentsOverflow;
    }
    return new Money(newUnits, newCents, currency);
  }

  public Money multiply(double timesValue) {
    double currentPrice = units + ((double) cents / 100);

    return new Money(currentPrice * timesValue, getCurrency());
  }

  public float toFloat() {
    return units + ((float) cents / 100);
  }
}
