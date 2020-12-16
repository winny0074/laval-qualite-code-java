package ca.ulaval.glo4002.reservation.interfaces.rest.Dto.FinancialRapport;

import ca.ulaval.glo4002.reservation.interfaces.rest.Dto.RoundDownAtTwoDigitsSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

public class FinancialReportDto {

    public final BigDecimal expense;
    public final BigDecimal income;
    public final BigDecimal profits;

    @JsonCreator
    public FinancialReportDto(@JsonProperty(value = "expense") @JsonSerialize(using = RoundDownAtTwoDigitsSerializer.class) BigDecimal expense,
                              @JsonProperty(value = "income") @JsonSerialize(using = RoundDownAtTwoDigitsSerializer.class)  BigDecimal income,
                              @JsonProperty(value = "profits") @JsonSerialize(using = RoundDownAtTwoDigitsSerializer.class)  BigDecimal profit) {
        this.expense = expense;
        this.income = income;
        this.profits = profit;
    }
}
