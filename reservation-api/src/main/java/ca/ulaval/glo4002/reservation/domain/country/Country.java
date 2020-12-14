package ca.ulaval.glo4002.reservation.domain.country;

public class Country {
  private final String code;
  private final String fullname;
  private final String currency;

  public Country(String code, String fullname, String currency) {
    this.code = code;
    this.fullname = fullname;
    this.currency = currency;
  }

  public String getCurrency() {
    return this.currency;
  }
}
