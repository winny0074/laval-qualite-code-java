package ca.ulaval.glo4002.reservation.domain.ingredient;

public enum LimitedIngredient {
  TOMATO("Tomato", 5);

  private final String name;
  private final int unavailableDurationInDays;

  LimitedIngredient(String name, int unavailableDurationInDays) {
    this.name = name;
    this.unavailableDurationInDays = unavailableDurationInDays;
  }

  public String getName() {
    return name;
  }

  public int getUnavailableDurationInDays() {
    return unavailableDurationInDays;
  }
}
