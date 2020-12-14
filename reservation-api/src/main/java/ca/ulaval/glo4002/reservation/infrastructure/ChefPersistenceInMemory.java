package ca.ulaval.glo4002.reservation.infrastructure;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefRepository;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import java.util.List;
import static ca.ulaval.glo4002.reservation.domain.Restriction.*;

public class ChefPersistenceInMemory implements ChefRepository {

  private final List<Chef> chefs;

  public ChefPersistenceInMemory() {
    chefs = loadChefs();
  }

  private List<Chef> loadChefs() {
    return List.of(
      new Chef("Thierry Aki", 1, new Money(6000d, "CAD$"), List.of(AUCUNE)),
      new Chef("Bob Smarties", 2, new Money(6000d, "CAD$"), List.of(VEGETALIENNE)),
      new Chef("Bob Rossbeef", 3, new Money(6000d, "CAD$"), List.of(VEGETARIENNE)),
      new Chef("Bill Adicion", 4, new Money(6000d, "CAD$"), List.of(ALLERGIE)),
      new Chef("Omar Calmar", 5, new Money(6000d, "CAD$"), List.of(MALADIE)),
      new Chef("Écharlotte Cardin", 6, new Money(6000d, "CAD$"), List.of(VEGETALIENNE, ALLERGIE)),
      new Chef("Éric Ardo", 7, new Money(6000d, "CAD$"), List.of(VEGETARIENNE, MALADIE)),
      new Chef("Hans Riz", 8, new Money(6000d, "CAD$"), List.of(AUCUNE, MALADIE)),
      new Chef("Amélie Mélo", 9, new Money(6000d, "CAD$"), List.of(VEGETALIENNE, ALLERGIE)));
  }

  @Override
  public List<Chef> findAll() {
    return chefs;
  }
}
