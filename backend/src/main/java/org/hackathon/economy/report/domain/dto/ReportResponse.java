package org.hackathon.economy.report.domain.dto;

import lombok.*;
import org.hackathon.economy.account.domain.DailyInterest;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Report 응답용 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class ReportResponse {
    private List<Object[]> memberData;
    private List<ChartData> interestData;
    private List<ChartData> balanceData;
    private List<ChartData> questData;
    private List<ChartData> scoreData;
    private List<DailyInterestDTO> interestHistory;
    private List<QuestDetailDTO> questDetails;
    private int totalPages;
    private int currentPage;
    private long totalItems;
    private Date questDate;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChartData {
        private String date;
        private long value;

        public static ChartData from(ChartDataDTO dto, Function<ChartDataDTO, Number> valueExtractor) {
            return new ChartData(
                    dto.getTodayDate().format(DateTimeFormatter.ISO_DATE),
                    valueExtractor.apply(dto).longValue()
            );
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestData {
        private String date;
        private int questCount;
        private int totalPoints;
        private List<QuestDetailDTO> details;
    }
}
