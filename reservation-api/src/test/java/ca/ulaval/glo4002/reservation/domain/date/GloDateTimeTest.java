package ca.ulaval.glo4002.reservation.domain.date;

import ca.ulaval.glo4002.reservation.domain.Utils.GloDateTime;
import ca.ulaval.glo4002.reservation.domain.exception.ingredientsReportsException.InvalidDate;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.InvalidFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GloDateTimeTest {
  private static final String INVALID_LOCAL_DATE_FORMAT = "01-01-2000";
  private static final String VALID_LOCAL_DATE_FORMAT = "2000-11-11";
  private final String VALID_ISO_DATE_TIME_FORMAT_NOT_UTC = "2150-01-01T00:00:00.000+01:00";
  private final String VALID_ISO_DATE_TIME_FORMAT = "2150-01-01T00:00:00.000Z";
  private final String SOME_DAY = "2150-07-20T00:00:00.000Z";
  private final String SAME_DAY_DIFFERENT_TIME = "2150-07-20T23:59:59.999Z";
  private final String THE_NEXT_DAY = "2150-07-21T00:00:00.000Z";
  private final String THE_SECOND_NEXT_DAY = "2150-07-22T00:00:00.000Z";
  private final int NUMBER_OF_DAYS_FROM_SOME_DAY_AND_THE_SECOND_NEXT_DAY = 2;
  private final int NUMBER_OF_DAYS_FROM_THE_SECOND_NEXT_DAY_AND_SOME_DAY = -2;

  @Test
  public void whenOfIsoDateTimeFormatWithDifferentOffset_thenThrowInvalidFormat() {
    Executable createOfIsoDateTimeFormat =
        () -> GloDateTime.ofIsoDateTimeFormat(VALID_ISO_DATE_TIME_FORMAT_NOT_UTC);

    assertThrows(InvalidFormat.class, createOfIsoDateTimeFormat);
  }

  @Test
  public void givenSpecialCharactersString_whenOfIsoDateTimeFormat_thenThrowInvalidFormat() {
    String specialCharacters = "&?$/%/";

    Executable createOfIsoDateTimeFormat =
            () -> GloDateTime.ofIsoDateTimeFormat(specialCharacters);

    assertThrows(InvalidFormat.class, createOfIsoDateTimeFormat);
  }

  @Test
  public void whenOfIsoDateTimeFormatWithValidFormat_thenNotThrow() {
    Executable createOfIsoDateTimeFormat =
        () -> GloDateTime.ofIsoDateTimeFormat(VALID_ISO_DATE_TIME_FORMAT);

    assertDoesNotThrow(createOfIsoDateTimeFormat);
  }

  @Test
  public void whenOfLocalDateFormatWithInvalidFormat_thenThrowInvalidDate() {
    Executable createOfIsoDateTimeFormat =
        () -> GloDateTime.ofLocalDateFormat(INVALID_LOCAL_DATE_FORMAT);

    assertThrows(InvalidDate.class, createOfIsoDateTimeFormat);
  }

  @Test
  public void whenOfLocalDateFormatWithValidFormat_thenNotThrow() {
    Executable createOfIsoDateTimeFormat =
        () -> GloDateTime.ofLocalDateFormat(VALID_LOCAL_DATE_FORMAT);

    assertDoesNotThrow(createOfIsoDateTimeFormat);
  }

  @Test
  public void givenDatesOfSameDay_whenIsSameDayAs_thenReturnTrue() {
    GloDateTime firstGloDateTime = GloDateTime.ofIsoDateTimeFormat(SOME_DAY);
    GloDateTime secondGloDateTime = GloDateTime.ofIsoDateTimeFormat(SAME_DAY_DIFFERENT_TIME);

    boolean datesAreOnSameDay = firstGloDateTime.isSameDayAs(secondGloDateTime);

    assertTrue(datesAreOnSameDay);
  }

  @Test
  public void givenDatesOfDifferentDays_whenIsSameDayAs_thenReturnFalse() {
    GloDateTime someDay = GloDateTime.ofIsoDateTimeFormat(SOME_DAY);
    GloDateTime theNextDay = GloDateTime.ofIsoDateTimeFormat(THE_NEXT_DAY);

    boolean datesAreOnSameDay = someDay.isSameDayAs(theNextDay);

    assertFalse(datesAreOnSameDay);
  }

  @Test
  public void givenTwoDays_whenCheckIfNextDayIsAfterSomeDay_thenReturnTrue() {
    GloDateTime someDay = GloDateTime.ofIsoDateTimeFormat(SOME_DAY);
    GloDateTime theNextDay = GloDateTime.ofIsoDateTimeFormat(THE_NEXT_DAY);

    boolean nextDayIsAfterSomeDay = theNextDay.isDateAfter(someDay);

    assertTrue(nextDayIsAfterSomeDay);
  }

  @Test
  public void givenTwoDays_whenCheckIfFirstDayIsAfterSecondDay_thenReturnFalse() {
    GloDateTime someDay = GloDateTime.ofIsoDateTimeFormat(SOME_DAY);
    GloDateTime theNextDay = GloDateTime.ofIsoDateTimeFormat(THE_NEXT_DAY);

    boolean datesAreConsecutive = someDay.isDateAfter(theNextDay);

    assertFalse(datesAreConsecutive);
  }

  @Test
  public void givenSomeDay_whenAddOneDay_thenReturnNextDay() {
    GloDateTime someDay = GloDateTime.ofIsoDateTimeFormat(SOME_DAY);
    GloDateTime theNextDay = GloDateTime.ofIsoDateTimeFormat(THE_NEXT_DAY);

    GloDateTime someDayPlusOne = someDay.plusDays(1);

    assertTrue(someDayPlusOne.isSameDayAs(theNextDay));
  }

  @Test
  public void
      givenThreeConsecutiveDays_whenGetInclusiveIntervalFromFirstToLast_thenReturnAllThreeDaysAndOnlyThose() {
    GloDateTime someDay = GloDateTime.ofIsoDateTimeFormat(SOME_DAY);
    GloDateTime theNextDay = GloDateTime.ofIsoDateTimeFormat(THE_NEXT_DAY);
    GloDateTime theSecondNextDay = GloDateTime.ofIsoDateTimeFormat(THE_SECOND_NEXT_DAY);
    List<GloDateTime> listOfThreeConsecutiveDays = List.of(someDay, theNextDay, theSecondNextDay);

    List<GloDateTime> inclusiveInterval = someDay.getInclusiveDateIntervalWith(theSecondNextDay);

    assertEquals(inclusiveInterval.size(), listOfThreeConsecutiveDays.size());
    assertTrue(areDaysInFirstListAllInSecondList(listOfThreeConsecutiveDays, inclusiveInterval));
  }

  @Test
  public void
  givenSomeDay_whenGetInclusiveIntervalFromSomeDayToSomeDay_thenReturnSomeDay() {
    GloDateTime someDay = GloDateTime.ofIsoDateTimeFormat(SOME_DAY);

    List<GloDateTime> inclusiveInterval = someDay.getInclusiveDateIntervalWith(someDay);

    assertEquals(inclusiveInterval.get(0), someDay);
  }

  @Test
  public void givenThreeConsecutiveDays_whenGetInclusiveIntervalFromLastToFirst_thenReturnAllThreeDaysAndOnlyThose() {
    GloDateTime someDay = GloDateTime.ofIsoDateTimeFormat(SOME_DAY);
    GloDateTime theNextDay = GloDateTime.ofIsoDateTimeFormat(THE_NEXT_DAY);
    GloDateTime theSecondNextDay = GloDateTime.ofIsoDateTimeFormat(THE_SECOND_NEXT_DAY);
    List<GloDateTime> listOfThreeConsecutiveDays = List.of(someDay, theNextDay, theSecondNextDay);

    List<GloDateTime> inclusiveInterval = theSecondNextDay.getInclusiveDateIntervalWith(someDay);

    assertEquals(inclusiveInterval.size(), listOfThreeConsecutiveDays.size());
    assertTrue(areDaysInFirstListAllInSecondList(listOfThreeConsecutiveDays, inclusiveInterval));
  }

  @Test
  public void givenSomeDayAndSecondNextDay_whenCountDayFromSecondNextDayToSomeDay_thenReturnCorrectNumberOfDays() {
    GloDateTime someDay = GloDateTime.ofIsoDateTimeFormat(SOME_DAY);
    GloDateTime theSecondNextDay = GloDateTime.ofIsoDateTimeFormat(THE_SECOND_NEXT_DAY);

    int daysBetweenTwoDays = someDay.countDaysFrom(theSecondNextDay);

    assertEquals(NUMBER_OF_DAYS_FROM_THE_SECOND_NEXT_DAY_AND_SOME_DAY, daysBetweenTwoDays);
  }

  @Test
  public void givenSomeDayAndSecondNextDay_whenCountDayFromSomeDayToSecondNextDay_thenReturnCorrectNumberOfDays() {
    GloDateTime someDay = GloDateTime.ofIsoDateTimeFormat(SOME_DAY);
    GloDateTime theSecondNextDay = GloDateTime.ofIsoDateTimeFormat(THE_SECOND_NEXT_DAY);

    int daysBetweenTwoDays = theSecondNextDay.countDaysFrom(someDay);

    assertEquals(NUMBER_OF_DAYS_FROM_SOME_DAY_AND_THE_SECOND_NEXT_DAY, daysBetweenTwoDays);
  }

  private boolean areDaysInFirstListAllInSecondList(
      List<GloDateTime> firstList, List<GloDateTime> secondList) {
    return secondList.stream()
        .map(
            gloDateTime1 ->
                firstList.stream().anyMatch(gloDateTime2 -> gloDateTime2.isSameDayAs(gloDateTime1)))
        .allMatch(Boolean::booleanValue);
  }
}
