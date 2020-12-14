package ca.ulaval.glo4002.reservation.domain.chef;

import java.util.ArrayList;
import java.util.List;

public class GlobalChefsReport {
    private List<DailyChefsReport> dailyChefReport;

    public GlobalChefsReport () {
        this.dailyChefReport = new ArrayList<>();
    }

    public GlobalChefsReport (List<DailyChefsReport> dailyChefReport) {
        this.dailyChefReport = dailyChefReport;
    }

    public List<DailyChefsReport> getDailyChefReport() {
        return dailyChefReport;
    }

    public void setDailyChefReport(List<DailyChefsReport> dailyChefReport) {
        this.dailyChefReport = dailyChefReport;
    }
}

