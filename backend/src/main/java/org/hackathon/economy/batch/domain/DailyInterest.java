package org.hackathon.economy.batch.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity // JPA가 이 클래스를 엔티티로 인식하게 함
@Table(name = "dilay_interest") // 이 엔티티가 DB의 user 테이블과 매핑됨을 명시 (기본적으로 "User" -> "user"로 인식)
@Data
public class DailyInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_interest_no")
    private Long dailyInterestNo; // 이자 내역 번호
    @Column(name = "account_no")
    private Long accountNo; // 계좌번호

    @Column(name = "today_balance")
    private Long todayBalance; // 당일 잔고
    @Column(name = "today_rate")
    private Long todayRate; // 당일 이자율
    @Column(name = "today_interest")
    private Boolean todayInterest; // 당일 이자
    @Column(name = "total_interest")
    private Boolean totalInterest; // 이자 누적량
    @Column(name = "today_grade")
    private Boolean todayGrade; // 당일 등급

    @Column(name = "current_daily")
    private Boolean currentDaily; // 일일 이자 적용 여부
    @Column(name = "current_weekly")
    private Boolean currentWeekly; // 주간 이자 적용 여부
    @Column(name = "current_monthly")
    private Boolean currentMonthly; // 월간 이자 적용 여부

    @Column(name = "today_date")
    private LocalDateTime todayDate; // 날짜

}


