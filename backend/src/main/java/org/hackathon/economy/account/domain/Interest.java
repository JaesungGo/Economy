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
    private Long interestNo;
    @Column(nullable = false)
    private Boolean dailyQuest;
    @Column(nullable = false)
    private Boolean weeklyQuest;
    @Column(nullable = false)
    private Boolean monthlyQuest;
    @Column(nullable = false)
    private Boolean currentDaily;
    @Column(nullable = false)
    private Boolean currentWeekly;
    @Column(nullable = false)
    private Boolean currentMonthly;
    @Column(nullable = false)
    private Date createTableDatetime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_no")
    private Account account;
}
