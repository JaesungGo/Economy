package org.hackathon.economy.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "DAILY_INTEREST")
public class DailyInterest {

    @Id @GeneratedValue
    private Long dailyInterestNo;
    @Column(nullable = false)
    private Long todayBalance;
    @Column(nullable = false)
    private Double todayRate;
    @Column(nullable = false)
    private Long todayInterest;
    @Column(nullable = false)
    private Long totalInterest;
    @Column(nullable = false)
    private Integer todayGrade;
    @Column(nullable = false)
    private Boolean currentDaily;
    @Column(nullable = false)
    private Boolean currentWeekly;
    @Column(nullable = false)
    private Boolean currentMonthly;
    @Column(nullable = false)
    private LocalDate todayDate;
    // localDate로 변경함

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_no")
    private Account account;
}