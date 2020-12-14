package ca.ulaval.glo4002.reservation.domain.ingredient;

public enum Allergen {
  CARROTS("Carrots");

  private final String name;

  Allergen(
    String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
