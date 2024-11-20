package org.hackathon.economy.report.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hackathon.economy.account.domain.DailyInterest;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class DailyInterestDTO {
    private Long dailyInterestNo;
    private Long accountNo;
    private Long todayBalance;
    private Double todayRate;
    private Long todayInterest;
    private Long totalInterest;
    private Integer todayGrade;
    private Boolean currentDaily;
    private Boolean currentWeekly;
    private Boolean currentMonthly;
    private LocalDate todayDate;

    public static DailyInterestDTO from(DailyInterest entity) {
        DailyInterestDTO dto = new DailyInterestDTO();
        dto.setDailyInterestNo(entity.getDailyInterestNo());
        dto.setAccountNo(entity.getAccount().getAccountNo());
        dto.setTodayBalance(entity.getTodayBalance());
        dto.setTodayRate(entity.getTodayRate());
        dto.setTodayInterest(entity.getTodayInterest());
        dto.setTotalInterest(entity.getTotalInterest());
        dto.setTodayGrade(entity.getTodayGrade());
        dto.setCurrentDaily(entity.getCurrentDaily());
        dto.setCurrentWeekly(entity.getCurrentWeekly());
        dto.setCurrentMonthly(entity.getCurrentMonthly());
        dto.setTodayDate(entity.getTodayDate());
        return dto;
    }
}