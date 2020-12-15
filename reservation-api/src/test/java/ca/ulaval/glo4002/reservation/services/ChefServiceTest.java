package ca.ulaval.glo4002.reservation.services;

import ca.ulaval.glo4002.reservation.domain.Restriction;
import static ca.ulaval.glo4002.reservation.domain.Restriction.*;
import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import ca.ulaval.glo4002.reservation.domain.chef.ChefReportRepository;
import ca.ulaval.glo4002.reservation.domain.chef.ChefRepository;
import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.exception.chefsReportsException.NoChefAvailableException;
import ca.ulaval.glo4002.reservation.domain.money.Money;
import ca.ulaval.glo4002.reservation.services.assemblers.GlobalChefsReportAssembler;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.mockito.Mockito.*;

import java.util.*;

class ChefServiceTest {

    public  ChefReportRepository       chefReportRepository;
    public  ChefRepository             chefRepository;
    public  GlobalChefsReportAssembler globalChefsReportAssembler;
    private ChefService                chefService;
    private ChefService                chefServiceSpy;
    private Chef                       chefOne;
    private Chef                       chefTwo;
    private final int                  HIGH_SENIORITY = 1;
    private final int                  LOW_SENIORITY  = 8;
    private final int                  HIGH_NUMBER_OF_SKILL  = 5;
    private final int                  LOW_NUMBER_OF_SKILL = 2;

    @BeforeEach
    public void setUp(){
        chefOne = mock(Chef.class);
        chefTwo = mock(Chef.class);
        chefReportRepository = mock(ChefReportRepository.class);
        chefRepository = mock(ChefRepository.class);
        globalChefsReportAssembler = mock(GlobalChefsReportAssembler.class);
        chefService = new ChefService(chefReportRepository, chefRepository, globalChefsReportAssembler);
        chefServiceSpy = spy(chefService);
    }

    @Test
    public void givenAListOfRestrictionAndAListOfChef_whenGroupChefByRestriction_thenReturnAMapWhereAllChefUnderARestrictionKeyHaveThatRestrictionAsASkill() {

        List<Chef> chefs = List.of(chefOne, chefTwo);
        List<Restriction> restrictions = List.of(ALLERGIE, VEGETALIENNE);

        doReturn(true).when(chefOne).hasRestriction(ALLERGIE);
        doReturn(true).when(chefTwo).hasRestriction(ALLERGIE);
        doReturn(true).when(chefOne).hasRestriction(VEGETALIENNE);
        doReturn(false).when(chefTwo).hasRestriction(VEGETALIENNE);


        Map<Restriction, List<Chef>> chefByRestriction = chefService.groupChefByRestriction(chefs, restrictions);

        assertTrue(restrictions.stream()
                              .map(restriction -> new AbstractMap.SimpleEntry(restriction, chefByRestriction.get(restriction)))
                              .allMatch(simpleEntry -> ((List<Chef>)simpleEntry.getValue()).stream()
                                                                                           .anyMatch(chef -> chef.hasRestriction((Restriction) simpleEntry.getKey()))));

    }

    @Test
    public void givenChefOneWithNumberOfSkillsInCurrentReservationGreaterThanChefTwo_whenFilterChefByCostOptimization_thenReturnChefOne() {

        List<Chef> chefs = List.of(chefOne, chefTwo);

        doReturn(HIGH_NUMBER_OF_SKILL)
               .when(chefOne)
               .getNumberOfSkillsInCurrentReservation();

        doReturn(LOW_NUMBER_OF_SKILL)
               .when(chefTwo)
               .getNumberOfSkillsInCurrentReservation();

        List<Chef> chefs1 = chefService.filterChefByCostOptimization(chefs);

        assertSame(chefOne, chefs1.get(0));
    }

    @Test
    public void givenChefOneWithHighSeniorityThanChefTwo_whenFilterChefByCostOptimization_thenReturnChefOne() {

        List<Chef> chefs = List.of(chefOne, chefTwo);

        doReturn(HIGH_SENIORITY).when(chefOne).getSeniorityOrder();
        doReturn(LOW_SENIORITY).when(chefTwo).getSeniorityOrder();

        List<Chef> chefs1 = chefService.filterChefBySeniority(chefs);

        assertSame(chefOne, chefs1.get(0));
    }

    @Test
    public void givenAMapWithChefOneAndChefTwoUnderAReservationKeyAndChefOneIsCostlyOptimize_whenGetRelevantChefByRestriction_thenReturnOnlyChefOneUnderThatRestrictionKeyBecauseIsMoreRelevant() {

        List<Chef> chefs = List.of(chefOne, chefTwo);
        Map<Restriction, List<Chef>> mapChef = Map.of(ALLERGIE, chefs);

        doReturn(List.of(chefOne))
               .when(chefServiceSpy)
               .filterChefByCostOptimization(chefs);

        Map<Restriction, Chef> filteredMap = chefServiceSpy.getRelevantChefByRestriction(mapChef);

        verify(chefServiceSpy, times(0)).filterChefBySeniority(anyList());

        assertSame(filteredMap.get(ALLERGIE), chefOne);
    }

    @Test
    public void givenAMapWithChefOneAndChefTwoEquallyOptimizeButChefOneHasHighSeniority_whenGetRelevantChefByRestriction_thenReturnOnlyChefOneUnderThatRestrictionKeyBecauseIsMoreRelevant() {

        List<Chef> chefs = List.of(chefOne, chefTwo);
        Map<Restriction, List<Chef>> mapChef = Map.of(ALLERGIE, chefs);

        doReturn(chefs)
               .when(chefServiceSpy)
               .filterChefByCostOptimization(chefs);

        doReturn(List.of(chefOne))
               .when(chefServiceSpy)
               .filterChefBySeniority(chefs);

        Map<Restriction, Chef> filteredMap = chefServiceSpy.getRelevantChefByRestriction(mapChef);

        verify(chefServiceSpy, times(1)).filterChefByCostOptimization(anyList());
        verify(chefServiceSpy, times(1)).filterChefBySeniority(anyList());

        assertSame(filteredMap.get(ALLERGIE), chefOne);
    }

    @Test
    public void givenAListOfChefAndAChef_whenFindNoRelevantChefsToBeDeleted_thenReturnOnlyChefOneUnderThatRestrictionKeyBecauseIsMoreRelevant() {

        List<Chef> chefs = List.of(chefOne);

        doReturn(true).when(chefOne).isMoreRelevant(chefTwo);

        List<Chef> noRelevantChefsToBeDeleted = chefServiceSpy.findNoRelevantChefsToBeDeleted(chefs, chefTwo,
                                                                                              (chef1, chef2) -> chef1.addReservation(chef2.getDailyReservationNumber()));

        assertSame(noRelevantChefsToBeDeleted.get(0), chefTwo);
    }

    @Test
    public void givenANewChefListContainingAchefAlreadyPresentInRepo_whenComputeOptimization_thenReturnTheListInRepo() {

        List<Chef> newChefs = List.of(buildChef());

        List<Chef> repoChefs = List.of(buildChef());

        List<Chef> chefs = chefService.computeOptimization(newChefs, List.copyOf(repoChefs));

        assertSame(chefs.get(0), repoChefs.get(0));
    }

    @Test
    public void givenANewChefListContainingAChefCostlyOptimizeTheAchefExistingInRepo_whenComputeOptimization_thenReturnAChefListWithTheNewChefAddedAndExistingChefRemoved() {
        List<Chef> newChefs = List.of(buildChef());

        Chef chefInRepo = buildChef1();
        List<Chef> repoChefs = new ArrayList<>();
        repoChefs.add(chefInRepo);

        doReturn(List.of(repoChefs.get(0)))
               .when(chefServiceSpy)
               .findNoRelevantChefsToBeDeleted(repoChefs, newChefs.get(0), (chef1, chef2) -> chef1.addReservation(chef2.getDailyReservationNumber()));

        List<Chef> chefs = chefServiceSpy.computeOptimization(newChefs, repoChefs);

        assertSame(chefs.get(0), newChefs.get(0));
        assertFalse(chefs.stream().anyMatch(chef -> chef.equal(chefInRepo)));
    }

    @Test
    public void givenADateAndARestrictionListWithNoChefAvailable_whenSave_thenThrowNoChefAvailableException() {
        GloDateTime date = GloDateTime.ofLocalDateFormat("2150-07-20");
        List<Restriction> restrictions = List.of(VEGETALIENNE);
        List<Chef> chefs = List.of(buildChef());

        doReturn(new HashMap<Restriction, List<Chef>>())
               .when(chefServiceSpy)
               .groupChefByRestriction(chefs, restrictions);

        doReturn(new HashMap<Restriction, List<Chef>>())
               .when(chefServiceSpy)
               .getRelevantChefByRestriction(anyMap());

        Executable executable = () -> chefServiceSpy.save(date, restrictions);

        assertThrows(NoChefAvailableException.class, executable);
    }

    @Test
    public void givenADateAndARestrictionListWithChefAvailable_whenSave_thenCallSaveMethodToSaveChefList() {
        GloDateTime date = GloDateTime.ofLocalDateFormat("2150-07-20");
        List<Restriction> restrictions = List.of(VEGETALIENNE);
        List<Chef> chefs = List.of(chefOne);

        doReturn(Map.of(VEGETALIENNE, chefs))
               .when(chefServiceSpy)
               .groupChefByRestriction(chefs, restrictions);

        doReturn(Map.of(VEGETALIENNE, chefs.get(0)))
               .when(chefServiceSpy)
               .getRelevantChefByRestriction(anyMap());

        doReturn(chefs)
               .when(chefServiceSpy)
               .optimizeCostWhileMergingWithChefInRepo(date, chefs);

        doNothing()
               .when(chefReportRepository)
               .save(date, chefs);


        chefServiceSpy.save(date, restrictions);

        verify(chefReportRepository, times(1)).save(date, chefs);
    }

    private Chef buildChef1() {
        List<Restriction> skills = List.of(ALLERGIE, AUCUNE);

        Money money = new Money(6000d, "CAD$");
        Chef chef = new Chef("Bob", 1, money, skills);

        chef.getReservationSkills().addAll(skills.subList(0,1));

        return chef;
    }

    private Chef buildChef() {
        List<Restriction> skills = List.of(ALLERGIE, AUCUNE, VEGETALIENNE);

        Money money = new Money(6000d, "CAD$");
        Chef chef = new Chef("Cube", 2, money, skills);

        chef.getReservationSkills().addAll(skills.subList(0,2));

        return chef;
    }
}
