package org.hackathon.economy.report.repository;

import org.hackathon.economy.account.domain.DailyInterest;
import org.hackathon.economy.report.domain.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ReportRepository extends JpaRepository<DailyInterest, String> {

    // 멤버 점수 및 등급 조회
    @Query(value = "SELECT m.member_name, m.member_grade, m.member_point FROM MEMBER m JOIN ACCOUNT a ON m.member_no = a.member_no WHERE a.account_no = :accountNo", nativeQuery = true)
    List<Object[]> findMemberGradeAndScoreByAccountNo(@Param("accountNo") Long accountNo);

    // 전체 이자내역 페이징
    @Query("SELECT di FROM DailyInterest di JOIN FETCH di.account a " +
            "WHERE a.accountNo = :accountNo AND di.todayDate BETWEEN :startDate AND :endDate " +
            "ORDER BY di.todayDate DESC")
    Page<DailyInterest> findAllInterestHistory(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
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

    // 일간 통합 데이터 쿼리 수정
    @Query(value = """
SELECT 
    targetDate AS date, 
    SUM(totalInterest) AS totalInterest, 
    AVG(avgBalance) AS avgBalance, 
    COUNT(DISTINCT targetDate) AS totalDays, 
    COALESCE(SUM(questCount), 0) AS questCount,
    COALESCE(SUM(totalPoints), 0) AS totalScore  
FROM (
    SELECT 
        DATE(di.todayDate) AS targetDate,
        di.todayInterest AS totalInterest,
        di.todayBalance AS avgBalance,
        COALESCE(qa.questCount, 0) AS questCount,
        COALESCE(q.totalPoints, 0) AS totalPoints
    FROM DAILY_INTEREST di 
    JOIN ACCOUNT a ON di.account_no = a.accountNo 
    JOIN MEMBER m ON a.member_no = m.memberNo 
    LEFT JOIN ( 
        SELECT 
            qa.member_no, 
            DATE(qa.achieveDatetime) AS achieveDate, 
            COUNT(*) AS questCount
        FROM QUEST_ACHEIVE qa
        GROUP BY qa.member_no, DATE(qa.achieveDatetime) 
    ) qa ON m.memberNo = qa.member_no 
        AND DATE(di.todayDate) = qa.achieveDate 
    LEFT JOIN (
        SELECT 
            qa.member_no,
            DATE(qa.achieveDatetime) AS achieveDate,
            SUM(q.questPoint) AS totalPoints
        FROM QUEST_ACHEIVE qa
        JOIN QUEST q ON  qa.quest_no = q.questNo  
        GROUP BY qa.member_no, DATE(qa.achieveDatetime)
    ) q ON m.memberNo = q.member_no 
        AND DATE(di.todayDate) = q.achieveDate
    WHERE di.account_no = :accountNo 
    AND di.todayDate BETWEEN :startDate AND :endDate 
) AS dailyData
GROUP BY targetDate 
ORDER BY targetDate""",
            nativeQuery = true)
    List<Object[]> getDailyIntegratedData(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 주간 통합 데이터 쿼리
    @Query(value = """
SELECT 
    MIN(targetDate) AS date, 
    SUM(totalInterest) AS totalInterest, 
    AVG(avgBalance) AS avgBalance, 
    COUNT(DISTINCT targetDate) AS totalDays, 
    COALESCE(SUM(questCount), 0) AS questCount,
    COALESCE(SUM(totalPoints), 0) AS totalScore
FROM (
    SELECT 
        DATE(di.todayDate) AS targetDate,
        YEARWEEK(di.todayDate, 1) AS weekGroup,
        di.todayInterest AS totalInterest,
        di.todayBalance AS avgBalance,
        COALESCE(qa.questCount, 0) AS questCount,
        COALESCE(q.totalPoints, 0) AS totalPoints
    FROM DAILY_INTEREST di 
    JOIN ACCOUNT a ON di.account_no = a.accountNo 
    JOIN MEMBER m ON a.member_no = m.memberNo 
    LEFT JOIN ( 
        SELECT 
            qa.member_no, 
            DATE(qa.achieveDatetime) AS achieveDate, 
            COUNT(*) AS questCount
        FROM QUEST_ACHEIVE qa
        GROUP BY qa.member_no, DATE(qa.achieveDatetime) 
    ) qa ON m.memberNo = qa.member_no 
        AND DATE(di.todayDate) = qa.achieveDate 
    LEFT JOIN (
        SELECT 
            qa.member_no,
            DATE(qa.achieveDatetime) AS achieveDate,
            SUM(q.questPoint) AS totalPoints
        FROM QUEST_ACHEIVE qa
        JOIN QUEST q ON  qa.quest_no = q.questNo  
        GROUP BY qa.member_no, DATE(qa.achieveDatetime)
    ) q ON m.memberNo = q.member_no 
        AND DATE(di.todayDate) = q.achieveDate
    WHERE di.account_no = :accountNo 
    AND di.todayDate BETWEEN :startDate AND :endDate 
) AS weeklyData
GROUP BY weekGroup
ORDER BY date""",
            nativeQuery = true)
    List<Object[]> getWeeklyIntegratedData(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 월간 통합 데이터 쿼리
    @Query(value = """
WITH monthlyData AS (
    SELECT 
        DATE(di.todayDate) AS targetDate,
        di.todayInterest,
        di.todayBalance,
        COALESCE(qa.questCount, 0) AS questCount,
        COALESCE(q.totalPoints, 0) AS totalPoints,
        LAST_DAY(di.todayDate) AS lastDayOfMonth
    FROM DAILY_INTEREST di 
    JOIN ACCOUNT a ON di.account_no = a.accountNo 
    JOIN MEMBER m ON a.member_no = m.memberNo 
    LEFT JOIN ( 
        SELECT 
            qa.member_no, 
            DATE(qa.achieveDatetime) AS achieveDate, 
            COUNT(*) AS questCount
        FROM QUEST_ACHEIVE qa
        GROUP BY qa.member_no, DATE(qa.achieveDatetime) 
    ) qa ON m.memberNo = qa.member_no 
        AND DATE(di.todayDate) = qa.achieveDate 
    LEFT JOIN (
        SELECT 
            qa.member_no,
            DATE(qa.achieveDatetime) AS achieveDate,
            SUM(q.questPoint) AS totalPoints
        FROM QUEST_ACHEIVE qa
        JOIN QUEST q ON  qa.quest_no = q.questNo  
        GROUP BY qa.member_no, DATE(qa.achieveDatetime)
    ) q ON m.memberNo = q.member_no 
        AND DATE(di.todayDate) = q.achieveDate
    WHERE di.account_no = :accountNo 
    AND di.todayDate BETWEEN :startDate AND :endDate 
)
SELECT 
    lastDayOfMonth AS date,
    SUM(todayInterest) AS totalInterest,
    AVG(todayBalance) AS avgBalance,
    COUNT(DISTINCT targetDate) AS totalDays,
    COALESCE(SUM(questCount), 0) AS questCount,
    COALESCE(SUM(totalPoints), 0) AS totalScore
FROM monthlyData
GROUP BY lastDayOfMonth
ORDER BY date""",
            nativeQuery = true)
    List<Object[]> getMonthlyIntegratedData(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 특정 날짜의 퀘스트 상세 내역 조회
    @Query(value = """
    SELECT 
        q.questContent,
        q.questPoint,
        qa.achieveDatetime,
        q.questType
    FROM ACCOUNT a
    JOIN MEMBER m ON a.member_no = m.memberNo
    JOIN QUEST_ACHEIVE qa ON m.memberNo = qa.member_no
    JOIN QUEST q ON  qa.quest_no = q.questNo
    WHERE a.accountNo = :accountNo
    AND DATE(qa.achieveDatetime) = :achieveDate
    ORDER BY qa.achieveDatetime DESC, q.questType ASC
    """, nativeQuery = true)
    List<Object[]> findQuestDetailsByDate(
            @Param("accountNo") Long accountNo,
            @Param("achieveDate") String achieveDate
    );

}