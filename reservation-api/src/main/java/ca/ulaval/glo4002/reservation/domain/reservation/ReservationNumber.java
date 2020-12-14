package ca.ulaval.glo4002.reservation.domain.reservation;

import java.text.MessageFormat;
import java.util.Objects;

public class ReservationNumber {

    private              String                     number;
    private static final String                     NUMBER_PATTERN        = "{0}-{1}";

    public static ReservationNumber create(String identifierPrefix, Long identifierSuffix) {
        return new ReservationNumber(MessageFormat.format(NUMBER_PATTERN, identifierPrefix, identifierSuffix));
    }

    public static ReservationNumber create(String appointmentNumberAsString) {
        return new ReservationNumber(appointmentNumberAsString);
    }

    private ReservationNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationNumber that = (ReservationNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }
}
