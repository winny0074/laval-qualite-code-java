package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.chef;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GlobalChefsReportDto {

    @JsonProperty("dates")
    private List<DailyChefsReportDto> dailyReport ;

    public GlobalChefsReportDto (@JsonProperty(value = "dates") List<DailyChefsReportDto> dailyReport) {
        this.dailyReport = dailyReport;
    }

    public List<DailyChefsReportDto> getDailyReport() {
        return dailyReport;
    }
}
