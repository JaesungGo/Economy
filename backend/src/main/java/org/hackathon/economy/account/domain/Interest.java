package org.hackathon.economy.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "INTEREST")
public class Interest {

    @Id @GeneratedValue
    @Column(name = "interest_no")
    private Long interestNo;
    @Column(name = "daily_quest", nullable = false)
    private Boolean dailyQuest;
    @Column(name = "weekly_quest", nullable = false)
    private Boolean weeklyQuest;
    @Column(name = "monthly_quest", nullable = false)
    private Boolean monthlyQuest;
    @Column(name = "current_daily", nullable = false)
    private Boolean currentDaily;
    @Column(name = "current_weekly", nullable = false)
    private Boolean currentWeekly;
    @Column(name = "current_monthly", nullable = false)
    private Boolean currentMonthly;
    @Column(name = "create_table_datetime", nullable = false)
    private Date createTableDatetime;
}
