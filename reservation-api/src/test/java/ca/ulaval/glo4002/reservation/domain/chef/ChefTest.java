package ca.ulaval.glo4002.reservation.domain.chef;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import static ca.ulaval.glo4002.reservation.domain.Restriction.*;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ChefTest {

  private Chef chef;
  private static final int MAX_NUMBER_OF_RESERVATIONS_PER_DAY = 5;

  @BeforeEach
  public void setUp(){
    chef = buildChef();
  }

  @Test
  public void givenARestriction_whenHasRestriction_thenReturnTrue(){
    assertTrue(chef.hasRestriction(ALLERGIE));
  }
  @Test
  public void givenARestrictionList_whenUpdateReservationSkills_thenChefReservationSkillShouldBeUpdated(){
    List<Restriction> reservationSkills = List.of(ALLERGIE, AUCUNE);

    chef.updateReservationSkills(reservationSkills);

    assertTrue(reservationSkills.stream()
                                .allMatch(restriction -> chef.getReservationSkills()
                                                              .contains(restriction)));
  }

  @Test
  public void givenNewChefWithLessReservationSkillList_whenIsMoreRelevant_thenShouldReturnTrue(){
    List<Restriction> newChefSkills = List.of(ALLERGIE);

    Money money = new Money(6000d, "CAD$");
    Chef newChef = new Chef("Ardo", 6, money, newChefSkills);
    newChef.getReservationSkills().addAll(newChefSkills);

    assertTrue(chef.isMoreRelevant(newChef));
  }

  @Test
  public void givenNewChefWithSameReservationSkillButWithLessSeniority_whenIsMoreRelevant_thenShouldReturnTrue(){
    List<Restriction> newChefSkills = List.of(ALLERGIE, AUCUNE);

    Money money = new Money(6000d, "CAD$");
    Chef newChef = new Chef("Ardo", 6, money, newChefSkills);
    newChef.getReservationSkills().addAll(newChefSkills);

    assertTrue(chef.isMoreRelevant(newChef));
  }

  @Test
  public void givenAChefWithReservationSkill_whenGetCurrentRestrictionChef_thenShouldBeNotNull() {
    assertNotNull(chef.getCurrentRestrictionsNames());
  }

  @Test
  public void givenAChefWithReservationLessThenTheMaximumRequired_whenHasMaxReservationReached_thenShouldReturnFalse() {
    Chef chef = buildChef();
    chef.addReservation(MAX_NUMBER_OF_RESERVATIONS_PER_DAY - 1);

    assertFalse(chef.hasMaxReservationReached());
  }

  @Test
  public void givenAChefWithReservationGreaterThenTheMaximumRequired_whenHasMaxReservationReached_thenShouldReturnTrue() {
    Chef chef = buildChef();
    chef.addReservation(MAX_NUMBER_OF_RESERVATIONS_PER_DAY + 1);

    assertTrue(chef.hasMaxReservationReached());
  }

  private Chef buildChef() {
    List<Restriction> skills = List.of(ALLERGIE, AUCUNE, VEGETALIENNE);

    Money money = new Money(6000d, "CAD$");
    Chef chef = new Chef("Cube", 2, money, skills);

    chef.getReservationSkills().addAll(List.of(ALLERGIE, AUCUNE));

    return chef;
  }
}
