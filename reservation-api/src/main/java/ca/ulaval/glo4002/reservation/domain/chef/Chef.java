package ca.ulaval.glo4002.reservation.domain.chef;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Chef {
  private final int               MAX_NUMBER_OF_RESERVATIONS_PER_DAY = 5;
  private final String            name;
  private final int               seniorityOrder;
  private final Money             chefCost;
  private final List<Restriction> skills;
  private int                     dailyReservationNumber;
  private List<Restriction>       reservationSkills;

  public Chef(String name,int seniorityOrder, Money chefCost, List<Restriction> skills) {
    this.name = name;
    this.seniorityOrder = seniorityOrder;
    this.chefCost = chefCost;
    this.skills = skills;
    this.reservationSkills = new ArrayList<>();
  }

  public int getSeniorityOrder() {
    return seniorityOrder;
  }

  public Money getPrice() {
    return chefCost;
  }

  public String getName() {
    return name;
  }

  public int getDailyReservationNumber() {
    return dailyReservationNumber;
  }

  public boolean hasRestriction(Restriction restriction) {
    return skills.stream().anyMatch(skill -> skill.equals(restriction));
  }

  public void updateReservationSkills(List<Restriction> restrictions) {
    List<String> names = restrictions.stream()
                                        .map(Restriction::name)
                                        .collect(Collectors.toList());
    List<Restriction> matchedRestrictions = skills.stream()
                                                  .filter(restriction -> names.contains(restriction.name()))
                                                  .collect(Collectors.toList());
    reservationSkills.addAll(matchedRestrictions);
  }

  public int getNumberOfSkillsInCurrentReservation() {
    return reservationSkills.size();
  }

  public boolean hasMaxReservationReached() {
    return dailyReservationNumber >= MAX_NUMBER_OF_RESERVATIONS_PER_DAY;
  }

  public boolean equal(Chef chef) {
    return this.name.equals(chef.name);
  }

  public boolean isMoreRelevant(Chef chef) {
    if(this.reservationSkills.containsAll(chef.reservationSkills)){
      if(this.reservationSkills.size() == chef.reservationSkills.size())
        return this.seniorityOrder < chef.seniorityOrder;
      return true;
    }
    return false;
  }

  public void addReservation(){ dailyReservationNumber += 1 ; }

  public List<Restriction> getReservationSkills() {
    return reservationSkills;
  }

  public List<String> getCurrentRestrictionsNames() {
    return reservationSkills.stream()
                            .map(Restriction::name)
                            .collect(Collectors.toList());
  }

  public void addReservation(int dailyNumberReservation) {
    this.dailyReservationNumber += dailyNumberReservation;
  }
}
