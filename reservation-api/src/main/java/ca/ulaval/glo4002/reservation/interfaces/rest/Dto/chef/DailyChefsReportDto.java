package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.chef;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DailyChefsReportDto {

    @JsonProperty(value = "date")
    public String date ;
    @JsonProperty(value = "chefs")
    public List<String> chefs ;
    @JsonProperty(value = "totalPrice")
    public long totalPrice ;

    public DailyChefsReportDto(
        @JsonProperty(value = "date") String date,
        @JsonProperty(value = "chefs") List<String> chefs,
        @JsonProperty(value = "totalPrice") long totalPrice){
        this.date = date;
        this.chefs = chefs;
        this.totalPrice = totalPrice;
    }

    public long getTotalPrice() {
        return totalPrice;
    }
}
