package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import ca.ulaval.glo4002.reservation.domain.chef.*;
import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.exception.chefsReportsException.NoChefAvailableException;
import ca.ulaval.glo4002.reservation.infrastructure.ChefReportPersistenceInMemory;
import ca.ulaval.glo4002.reservation.infrastructure.ChefPersistenceInMemory;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.chef.GlobalChefsReportDto;
import ca.ulaval.glo4002.reservation.services.assemblers.GlobalChefsReportAssembler;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ChefService {
  public ChefReportRepository chefReportRepository;
  public ChefRepository chefRepository;
  public GlobalChefsReportAssembler globalChefsReportAssembler;

  @Inject
  public ChefService(ChefReportRepository chefReportRepository, ChefRepository chefRepository, GlobalChefsReportAssembler globalChefsReportAssembler) {
    this.chefReportRepository = chefReportRepository;
    this.chefRepository = chefRepository;
    this.globalChefsReportAssembler = globalChefsReportAssembler;
  }

  public void save(GloDateTime date, List<Restriction> restrictionList ) {

    List<Chef> chefs = this.chefRepository.findAll();
    chefs.forEach(chef -> chef.updateReservationSkills(restrictionList));

    Map<Restriction,List<Chef>> chefWithSkills = groupChefByRestriction(chefs, restrictionList);
    Map<Restriction, Chef> relevantChefByRestriction = getRelevantChefByRestriction(chefWithSkills);

    if (relevantChefByRestriction.keySet().size() < restrictionList.size()) {
      throw new NoChefAvailableException();
    }

    List<Chef> selectedChefs = relevantChefByRestriction.values()
                                                        .stream()
                                                        .distinct()
                                                        .collect(Collectors.toList());

    selectedChefs = optimizeCostWhileMergingWithChefInRepo(date, selectedChefs);
    //selectedChefs.stream().forEach(Chef::addReservation);

    chefReportRepository.save(date, selectedChefs);
  }

  public Map<Restriction,List<Chef>> groupChefByRestriction(List<Chef> chefs, List<Restriction> allRestriction) {
      Map<Restriction,List<Chef>> chefsMap = new HashMap<>();
      allRestriction.forEach(restriction -> {
        List<Chef> chefList = chefs.stream()
                                    .filter(chef -> chef.hasRestriction(restriction))
                                    .collect(Collectors.toList());
        chefsMap.put(restriction, chefList);
      });

    return chefsMap;
  }

  public Map<Restriction,Chef> getRelevantChefByRestriction(Map<Restriction,List<Chef>> chefMap) {
    Map<Restriction,Chef> tempChefMap = new HashMap<>();

    chefMap.entrySet().stream()
           .forEach(restrictionEntry -> {
              List<Chef> chefs = restrictionEntry.getValue();
              List<Chef> chefList = filterChefByCostOptimization(chefs);
              if(chefList.size() > 1){
                chefList = filterChefBySeniority(chefList);
              }
              tempChefMap.put(restrictionEntry.getKey(),chefList.get(0));
            });
    return tempChefMap;
  }

  public List<Chef> filterChefByCostOptimization(List<Chef> chefs) {
    Integer maxSkillNumber = chefs.stream()
                                  .map(Chef::getNumberOfSkillsInCurrentReservation)
                                  .max(Integer::compare).get();
    return  chefs.stream()
                  .filter(chef -> maxSkillNumber.equals(chef.getNumberOfSkillsInCurrentReservation()))
                  .collect(Collectors.toList());
  }

  public List<Chef> filterChefBySeniority(List<Chef> chefs) {
    Integer minSkillNumber = chefs.stream()
                                  .map(Chef::getSeniorityOrder)
                                  .min(Integer::compare).get();
    return chefs.stream()
                .filter(chef -> minSkillNumber.equals(chef.getSeniorityOrder()))
                .collect(Collectors.toList());
  }

  public List<Chef> optimizeCostWhileMergingWithChefInRepo(GloDateTime date, List<Chef> chefs) {
    List<Chef> chefsFromRepo = chefReportRepository.findChefsByDate(date);

    if(chefsFromRepo.isEmpty()) {
      return chefs;
    }

    chefReportRepository.removeEntryByDay(date);

    return computeOptimization(chefs, chefsFromRepo);
  }

  public List<Chef> computeOptimization(List<Chef> chefs, List<Chef> chefsFromRepo) {
    chefs.forEach(chef -> {

      Optional<Chef> repoChef = chefsFromRepo.stream().filter(currentChef -> currentChef.equal(chef)).findAny();

      if(!repoChef.isPresent()) {
        BiConsumer<Chef, Chef> tranfertDailyReservationNumber = (chef1, chef2) -> chef1.addReservation(chef2.getDailyReservationNumber());

        List<Chef> chefToBeDelete = findNoRelevantChefsToBeDeleted(chefsFromRepo, chef, tranfertDailyReservationNumber);

        chefsFromRepo.add(chef);

        if(!chefToBeDelete.isEmpty()){
          Predicate<Chef> chefPredicate = chef1 -> chefToBeDelete.stream()
                                                                  .anyMatch(deletedChef -> deletedChef.equal(chef1));
          chefsFromRepo.removeIf(chefPredicate);
        }
      }
      else{
        repoChef.get().addReservation();
        repoChef.get().updateReservationSkills(chef.getReservationSkills());
      }
    });
    return chefsFromRepo;
  }

  public List<Chef> findNoRelevantChefsToBeDeleted(List<Chef> currentChefs, Chef chef, BiConsumer<Chef, Chef> consumer) {
    return currentChefs.stream()
                        .filter(chef1 -> !chef1.hasMaxReservationReached())
                        .map(chef1 -> {
                          if(chef.isMoreRelevant(chef1)){
                              consumer.accept(chef, chef1);
                            return chef1;
                          }
                          else if( chef1.isMoreRelevant(chef)){
                              consumer.accept(chef, chef1);
                            return chef;
                          }
                          return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
  }

  public GlobalChefsReportDto getGlobalChefReport(){
    Map<String, List<Chef>> aggregatedDataByDay = chefReportRepository.getAggregatedDataByDay();
    GlobalChefsReport globalChefsReport = new GlobalChefsReport();

    aggregatedDataByDay.entrySet().forEach(entrySet -> {
      String date = entrySet.getKey();
      List<Chef> chefs = entrySet.getValue();
      List<String> chefNames = chefs.stream()
                                    .map(Chef::getName)
                                    .sorted()
                                    .collect(Collectors.toList());

      long totalPrice = chefs.stream().mapToLong(chef -> chef.getPrice().getUnits()).sum();

      globalChefsReport.getDailyChefReport().add(new DailyChefsReport(date, chefNames, totalPrice));
    });

    globalChefsReport.getDailyChefReport()
                     .stream()
                     .sorted(Comparator.comparing(DailyChefsReport::getDate));

    return globalChefsReportAssembler.from(globalChefsReport);
  }

  public BigDecimal getTotalExpense() {
    GlobalChefsReportDto globalChefReport = getGlobalChefReport();
    return globalChefReport.getDailyReport()
                           .stream()
                           .map(rapot -> rapot.getTotalPrice())
                           .map(BigDecimal::new)
                           .reduce(BigDecimal.valueOf(0), BigDecimal::add);
  }
}
