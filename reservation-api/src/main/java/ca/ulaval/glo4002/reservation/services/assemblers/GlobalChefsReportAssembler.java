package ca.ulaval.glo4002.reservation.services.assemblers;

import ca.ulaval.glo4002.reservation.domain.chef.DailyChefsReport;
import ca.ulaval.glo4002.reservation.domain.chef.GlobalChefsReport;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.chef.DailyChefsReportDto;
import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.chef.GlobalChefsReportDto;
import java.util.ArrayList;
import java.util.List;

public class GlobalChefsReportAssembler {

    public GlobalChefsReport from(GlobalChefsReportDto globalChefsReportDto){

        GlobalChefsReport globalChefsReport = new GlobalChefsReport();
        List<DailyChefsReport> dailyChefsReports = new ArrayList<>();
        for (DailyChefsReportDto dailyChefsReportDto : globalChefsReportDto.getDailyReport()) {

            DailyChefsReport dailyChefsReport = new DailyChefsReport(dailyChefsReportDto.date,dailyChefsReportDto.chefs, dailyChefsReportDto.totalPrice);
            dailyChefsReports.add(dailyChefsReport);
        }
        globalChefsReport.setDailyChefReport(dailyChefsReports);

        return globalChefsReport;
    }

    public GlobalChefsReportDto from(GlobalChefsReport globalChefsReport) {

        List<DailyChefsReportDto> dailyChefsReportsDto = new ArrayList<>();
        for (DailyChefsReport dailyChefsReport : globalChefsReport.getDailyChefReport()) {

            DailyChefsReportDto dailyChefsReportDto = new DailyChefsReportDto(dailyChefsReport.getDate(),dailyChefsReport.getChefs(), dailyChefsReport.getTotalPrice());
            dailyChefsReportsDto.add(dailyChefsReportDto);
        }

        return  new GlobalChefsReportDto(dailyChefsReportsDto);
    }
}
