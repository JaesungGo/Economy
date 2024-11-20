package org.hackathon.economy.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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
    private Long monthlyInterest;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_no")
    private Account account;
}