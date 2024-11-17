package org.hackathon.economy.report.repository;

import org.hackathon.economy.account.domain.DailyInterest;
import org.hackathon.economy.report.domain.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<DailyInterest, String> {

    // 일별 상세 데이터 조회
    @Query("SELECT di FROM DailyInterest di JOIN FETCH di.account a " +
            "WHERE a.accountNo = :accountNo AND di.todayDate BETWEEN :startDate AND :endDate " +
            "ORDER BY di.todayDate")
    List<DailyInterest> findDailyData(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 일간 데이터 조회
    @Query("SELECT NEW org.hackathon.economy.report.domain.dto.ChartDataDTO(" +
            "di.todayDate, " +
            "SUM(di.todayInterest), " +
            "AVG(di.todayBalance), " +
            "COUNT(di)) " +
            "FROM DailyInterest di " +
            "WHERE di.account.accountNo = :accountNo " +
            "AND di.todayDate BETWEEN :startDate AND :endDate " +
            "GROUP BY di.todayDate ORDER BY di.todayDate"
    )
    List<ChartDataDTO> getDailyChartData(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 주간 데이터 조회 - MySQL 네이티브 쿼리 사용
    @Query(value = "SELECT NEW org.hackathon.economy.report.domain.dto.ChartDataDTO(" +
            "STR_TO_DATE(DATE_FORMAT(di.today_date, '%Y-%m-%d') - INTERVAL (DAYOFWEEK(di.today_date) - 1) DAY, '%Y-%m-%d'), " +
            "SUM(di.today_interest), " +
            "AVG(di.today_balance), " +
            "COUNT(*)) " +
            "FROM daily_interest di " +
            "WHERE di.account_no = :accountNo " +
            "AND di.today_date BETWEEN :startDate AND :endDate " +
            "GROUP BY STR_TO_DATE(DATE_FORMAT(di.today_date, '%Y-%m-%d') - INTERVAL (DAYOFWEEK(di.today_date) - 1) DAY, '%Y-%m-%d') " +
            "ORDER BY STR_TO_DATE(DATE_FORMAT(di.today_date, '%Y-%m-%d') - INTERVAL (DAYOFWEEK(di.today_date) - 1) DAY, '%Y-%m-%d')",
            nativeQuery = true)
    List<Object[]> getWeeklyChartData(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 월간 데이터 조회 - MySQL 네이티브 쿼리 사용
    @Query(value = "SELECT NEW org.hackathon.economy.report.domain.dto.ChartDataDTO(" +
            "DATE_FORMAT(di.today_date, '%Y-%m-01'), " +
            "SUM(di.today_interest), " +
            "AVG(di.today_balance), " +
            "COUNT(*)) " +
            "FROM daily_interest di " +
            "WHERE di.account_no = :accountNo " +
            "AND di.today_date BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE_FORMAT(di.today_date, '%Y-%m-01') " +
            "ORDER BY DATE_FORMAT(di.today_date, '%Y-%m-01')",
            nativeQuery = true)
    List<Object[]> getMonthlyChartData(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 퀘스트 달성 데이터 조회
    @Query(value = "SELECT COUNT(*) " +
            "FROM quest_achieve qa " +
            "JOIN member m ON qa.member_no = m.member_no " +
            "JOIN account a ON a.member_no = m.member_no " +
            "WHERE a.account_no = :accountNo " +
            "AND DATE(qa.achieve_datetime) BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(qa.achieve_datetime) " +
            "ORDER BY DATE(qa.achieve_datetime)",
            nativeQuery = true)
    List<Long> getQuestCounts(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}