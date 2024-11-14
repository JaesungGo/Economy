package org.hackathon.economy.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "DAILY_INTEREST")
public class DailyInterest {

    @Id @GeneratedValue
    @Column(name = "daily_interest_no")
    private Long dailyInterestNo;
    @Column(name = "today_interest", nullable = false)
    private Double todayInterest;
    @Column(name = "rank_interest", nullable = false)
    private Double rankInterest;
    @Column(name = "current_daily", nullable = false)
    private Boolean currentDaily;
    @Column(name = "current_weekly", nullable = false)
    private Boolean currentWeekly;
    @Column(name = "current_monthly", nullable = false)
    private Boolean currentMonthly;
    @Column(name = "today_date", nullable = false)
    private Date todayDate;
}
