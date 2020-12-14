package ca.ulaval.glo4002.reservation.domain.ingredient.meal;

public abstract class MealType implements Comparable<MealType> {
  private String name;
  private float quantity;

  public MealType(String name, float quantityInKg) {
    this.setName(name);
    this.setQuantity(quantityInKg);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getQuantity() {
    return quantity;
  }

  public void setQuantity(float quantity) {
    this.quantity = quantity;
  }

  public int compareTo(MealType mealType) {
    int comparison = name.compareTo(mealType.getName());
    if (comparison == 0) {
      if (quantity < mealType.getQuantity()) {
        return -1;
      } else if (quantity > mealType.getQuantity()) {
        return 1;
      } else {
        return 0;
      }
    } else {
      return comparison;
    }
  }
}
