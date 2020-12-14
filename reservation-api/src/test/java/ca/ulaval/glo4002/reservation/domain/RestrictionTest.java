package ca.ulaval.glo4002.reservation.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestrictionTest {
  private static final Restriction A_RESTRICTION = Restriction.MALADIE;

  @Test
  public void whenFromName_shouldReturnCorrectRestriction() {
    Restriction returnedRestriction = Restriction.fromName(A_RESTRICTION.toString());

    assertEquals(A_RESTRICTION, returnedRestriction);
  }
}
