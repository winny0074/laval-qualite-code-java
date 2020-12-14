package ca.ulaval.glo4002.reservation.domain.chef;

import ca.ulaval.glo4002.reservation.domain.date.GloDateTime;
import java.util.List;
import java.util.Map;

public interface ChefReportRepository {

    void save(GloDateTime date, List<Chef> chef);

    List<Chef> findChefsByDate(GloDateTime date);

    Map<String, List<Chef>> getAggregatedDataByDay();

    void removeEntryByDay(GloDateTime date);
}
