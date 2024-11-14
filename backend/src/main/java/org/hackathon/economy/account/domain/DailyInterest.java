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
    private Long dailyInterestNo;
    @Column(nullable = false)
    private Double todayInterest;
    @Column(nullable = false)
    private Double rankInterest;
    @Column(nullable = false)
    private Boolean currentDaily;
    @Column(nullable = false)
    private Boolean currentWeekly;
    @Column(nullable = false)
    private Boolean currentMonthly;
    @Column(nullable = false)
    private Date todayDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_no")
    private Account account;
}
