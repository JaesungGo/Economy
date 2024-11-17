package org.hackathon.economy.report.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hackathon.economy.account.domain.DailyInterest;

import java.util.List;
import java.util.function.Function;

/**
 * Report 응답용 DTO
 */
@Getter @Setter
public class ReportResponse {
    private List<ChartData> interestData;
    private List<ChartData> questData;
    private List<ChartData> balanceData;
    private List<DailyInterestDTO> interestHistory;

    @Getter @Setter
    public static class ChartData {
        private String date;
        private Long value;

        public static ChartData from(ChartDataDTO data, Function<ChartDataDTO, Long> valueExtractor) {
            ChartData chartData = new ChartData();
            chartData.setDate(data.getTodayDate().toString());
            chartData.setValue(valueExtractor.apply(data));
            return chartData;
        }
    }
}
