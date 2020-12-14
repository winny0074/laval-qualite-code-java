package ca.ulaval.glo4002.reservation.domain.date;

import ca.ulaval.glo4002.reservation.domain.exception.ingredientsReportsException.InvalidDate;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.InvalidFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class GloDateTime {
  private final OffsetDateTime dateTime;

  private GloDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public static GloDateTime ofIsoDateTimeFormat(String iso8601DateTime) {
    try {
      OffsetDateTime dateTime =
          OffsetDateTime.parse(iso8601DateTime, DateTimeFormatter.ISO_DATE_TIME);
      if (dateTime.getOffset() != ZoneOffset.UTC) {
        throw new InvalidFormat();
      }
      return new GloDateTime(dateTime);
    } catch (DateTimeParseException ex) {
      throw new InvalidFormat();
    }
  }

  public static GloDateTime ofLocalDateFormat(String isoLocalDate) {
    try {
      LocalDate localDate = LocalDate.parse(isoLocalDate, DateTimeFormatter.ISO_LOCAL_DATE);
      OffsetDateTime dateTime = OffsetDateTime.of(localDate.atStartOfDay(), ZoneOffset.UTC);
      return new GloDateTime(dateTime);
    } catch (DateTimeParseException ex) {
      throw new InvalidDate("", "");
    }
  }

  public boolean isSameDayAs(GloDateTime secondGloDateTime) {
    return toLocalDate().isEqual(secondGloDateTime.toLocalDate());
  }

  public boolean isDateBefore(GloDateTime otherGloDateTime) {
    return toLocalDate().isBefore(otherGloDateTime.toLocalDate());
  }

  public boolean isDateAfter(GloDateTime otherGloDateTime) {
    return toLocalDate().isAfter(otherGloDateTime.toLocalDate());
  }

  public GloDateTime plusDays(long numberOfDays) {
    return new GloDateTime(dateTime.plusDays(numberOfDays));
  }

  public LocalDate toLocalDate() {
    return dateTime.toLocalDate();
  }

  public String toLocalDateFormat() {
    return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  public String toIsoDateTimeFormat() {
    return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
  }

  public String toPresentationDateFormat() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d uuuu", Locale.US);
    return dateTime.toLocalDate().format(formatter);
  }

  public List<GloDateTime> getInclusiveDateIntervalWith(GloDateTime otherDate) {
    if (isSameDayAs(otherDate)) {
      return Collections.singletonList(this);
    } else {
      GloDateTime firstDate;
      GloDateTime secondDate;
      if (this.isDateBefore(otherDate)) {
        firstDate = this;
        secondDate = otherDate;
      } else {
        firstDate = otherDate;
        secondDate = this;
      }
      return makeInclusiveDateIntervalFromTo(firstDate, secondDate);
    }
  }

  private List<GloDateTime> makeInclusiveDateIntervalFromTo(
      GloDateTime startDate, GloDateTime endDate) {
    List<GloDateTime> datesInInterval = new LinkedList<>();

    GloDateTime date = startDate;
    while (!date.isSameDayAs(endDate)) {
      datesInInterval.add(date);
      date = date.plusDays(1);
    }
    datesInInterval.add(endDate);
    return datesInInterval;
  }

  public int countDaysFrom(GloDateTime date) {
    return dateTime.toLocalDate().compareTo(date.toLocalDate());
  }
}
