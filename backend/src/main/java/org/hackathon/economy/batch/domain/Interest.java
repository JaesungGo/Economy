package org.hackathon.economy.batch.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity // JPA가 이 클래스를 엔티티로 인식하게 함
@Table(name = "interest") // 이 엔티티가 DB의 user 테이블과 매핑됨을 명시 (기본적으로 "User" -> "user"로 인식)
@Data
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_no")
    private Long interestNo;
    @Column(name = "account_no")
    private Long accountNo;

    @Column(name = "daily_quest")
    private Boolean dailyQuest;
    @Column(name = "weekly_quest")
    private Boolean weeklyQuest;
    @Column(name = "monthly_quest")
    private Boolean monthlyQuest;

    @Column(name = "current_daily")
    private Boolean currentDaily;
    @Column(name = "current_weekly")
    private Boolean currentWeekly;
    @Column(name = "current_monthly")
    private Boolean currentMonthly;

    @Column(name = "create_table_datetime")
    private LocalDateTime createTableDatetime;

}


