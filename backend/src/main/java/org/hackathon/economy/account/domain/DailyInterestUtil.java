package org.hackathon.economy.account.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DailyInterestUtil {
    private Long todayBalance;
    private Double todayRate;
    private Long todayInterest;
    private Long monthlyInterest;
    private Long totalInterest;
    private Integer todayGrade;
    private LocalDate todayDate;
}
