package ca.ulaval.glo4002.reservation.domain.chef;

import java.util.List;

public class DailyChefsReport {
  private final String date ;
  private final List<String> chefs ;
  private final long totalPrice ;

  public DailyChefsReport(String date, List<String> chefs, long totalPrice) {
    this.date = date;
    this.chefs = chefs;
    this.totalPrice = totalPrice;
  }

  public String getDate() {
    return date;
  }

  public List<String> getChefs() {
    return chefs;
  }

  public long getTotalPrice() {
    return totalPrice;
  }

}
