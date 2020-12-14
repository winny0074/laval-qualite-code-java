package ca.ulaval.glo4002.reservation.infrastructure;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefReportRepository;
import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChefReportPersistenceInMemory implements ChefReportRepository {
  private static ChefReportPersistenceInMemory     instance;
  private final  ConcurrentMap<String, List<Chef>> chefListMap;

  public ChefReportPersistenceInMemory() {
    chefListMap = new ConcurrentHashMap<>();
  }

  public ChefReportPersistenceInMemory(ConcurrentMap<String, List<Chef>> chefListMap) {
    this.chefListMap = chefListMap;
  }

  public static ChefReportPersistenceInMemory getInstance() {
    // https://www.javacodemonk.com/threadsafe-singleton-design-pattern-java-806ad7e6

    ChefReportPersistenceInMemory localInstanceReference = instance;
    if (localInstanceReference == null) {
      synchronized (ChefReportPersistenceInMemory.class) {
        localInstanceReference = instance;
        if (localInstanceReference == null) instance = new ChefReportPersistenceInMemory();
      }
    }
    return instance;
  }

  @Override
  public void save(GloDateTime date, List<Chef> chefs){
    List<Chef> chefList = new ArrayList<>(chefs);
    chefListMap.put(date.toLocalDateFormat(),chefList);
  }



  @Override
  public Map<String, List<Chef>>  getAggregatedDataByDay(){
    return chefListMap;
  }

  @Override
  public void removeEntryByDay(GloDateTime date) {
    chefListMap.remove(date.toLocalDateFormat());
  }

  @Override
  public List<Chef> findChefsByDate(GloDateTime date) {
    List<Chef> chefs = chefListMap.get(date.toLocalDateFormat());
    if(Objects.isNull(chefs)) {
      return new ArrayList<>();
    }
    return chefListMap.get(date.toLocalDateFormat());
  }
}
