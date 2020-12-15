package ca.ulaval.glo4002.reservation.infrastructure;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import static org.junit.jupiter.api.Assertions.*;

public class ChefReportPersistenceInMemoryTest {

  ChefReportPersistenceInMemory chefReportPersistenceInMemory;

  private ConcurrentMap<String, List<Chef>> chefListMap;
  private GloDateTime A_GIVEN_DATE = GloDateTime.ofIsoDateTimeFormat("1122-11-11T00:00:00.000Z");
  Money money = new Money(6000d, "CAD$");

  @BeforeEach
  public void  setup(){
    chefListMap = new ConcurrentHashMap<>();
      chefReportPersistenceInMemory = new ChefReportPersistenceInMemory(chefListMap);
  }

  @Test
  public void givenAGivenDate_whenFindChefByDate_thenReturnListOfChef(){
    chefListMap.put(A_GIVEN_DATE.toLocalDateFormat(), getChefs());

    List<Chef> chefsByDate = chefReportPersistenceInMemory.findChefsByDate(A_GIVEN_DATE);

    assertNotNull(chefsByDate);
  }

  @Test
  public void givenADateAndChefList_whenSave_thenNewChefListShouldBeSaved(){

    List<Restriction> restrictionList = new ArrayList<>();
    restrictionList.add(Restriction.AUCUNE);
    Chef chef1 = new Chef("Cube Eric", 8, money, restrictionList);
    chef1.getReservationSkills().add(Restriction.AUCUNE);

    chefReportPersistenceInMemory.save(A_GIVEN_DATE, List.of(chef1));


    assertTrue(chefListMap.get(A_GIVEN_DATE.toLocalDateFormat())
                              .stream()
                              .anyMatch(chef -> chef.equal(chef1)));
  }


  public List<Chef> getChefs(){

    List<Restriction> restrictionList = new ArrayList<>();
    restrictionList.add(Restriction.ALLERGIE);
    restrictionList.add(Restriction.AUCUNE);
    restrictionList.add(Restriction.VEGETALIENNE);

    List<Chef> chefs = new ArrayList<>();
    Chef chef = new Chef("Cube", 8, money, restrictionList);
    chef.getReservationSkills().add(Restriction.ALLERGIE);
    chef.getReservationSkills().add(Restriction.VEGETALIENNE);
    chefs.add(chef);

    return chefs;
  }

}
