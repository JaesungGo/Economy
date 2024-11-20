package org.hackathon.economy.report.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ChartDataDTO {
    private LocalDate todayDate;
    private Long totalInterest;
    private Double averageBalance;
    private Long count;

    public ChartDataDTO(LocalDate todayDate, Long totalInterest, Double averageBalance, Long count) {
        this.todayDate = todayDate;
        this.totalInterest = totalInterest;
        this.averageBalance = averageBalance;
        this.count = count;
    }

    public ChartDataDTO(Object[] values) {
        this.todayDate = ((java.sql.Date) values[0]).toLocalDate();
        this.totalInterest = ((Number) values[1]).longValue();
        this.averageBalance = ((Number) values[2]).doubleValue();
        this.count = ((Number) values[3]).longValue();
    }
}