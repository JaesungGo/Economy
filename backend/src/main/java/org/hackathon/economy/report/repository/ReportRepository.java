package org.hackathon.economy.report.repository;

import org.hackathon.economy.account.domain.DailyInterest;
import org.hackathon.economy.member.domain.Member;
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
    @Query(value = "SELECT m.member_name, m.member_grade, m.member_point FROM member m JOIN account a ON m.member_no = a.member_no WHERE a.account_no = :accountNo", nativeQuery = true)
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
// 일간 통합 데이터 쿼리
    @Query(value = """
    SELECT 
        target_date AS date, 
        SUM(total_interest) AS total_interest, 
        AVG(avg_balance) AS avg_balance, 
        COUNT(DISTINCT target_date) AS total_days, 
        COALESCE(SUM(quest_count), 0) AS quest_count,
        COALESCE(SUM(total_points), 0) AS total_score  
    FROM (
        SELECT 
            DATE(di.today_date) AS target_date,
            di.today_interest AS total_interest,
            di.today_balance AS avg_balance,
            COALESCE(qa.quest_count, 0) AS quest_count,
            COALESCE(q.total_points, 0) AS total_points
        FROM daily_interest di 
        JOIN account a ON di.account_no = a.account_no 
        JOIN member m ON a.member_no = m.member_no 
        LEFT JOIN ( 
            SELECT 
                qa.member_no, 
                DATE(qa.achieve_datetime) AS achieve_date, 
                COUNT(*) AS quest_count
            FROM quest_achieve qa
            GROUP BY qa.member_no, DATE(qa.achieve_datetime) 
        ) qa ON m.member_no = qa.member_no 
            AND DATE(di.today_date) = qa.achieve_date 
        LEFT JOIN (
            SELECT 
                qa.member_no,
                DATE(qa.achieve_datetime) AS achieve_date,
                SUM(q.quest_point) AS total_points
            FROM quest_achieve qa
            JOIN quest q ON qa.quest_no = q.quest_no  -- 수정된 부분
            GROUP BY qa.member_no, DATE(qa.achieve_datetime)
        ) q ON m.member_no = q.member_no 
            AND DATE(di.today_date) = q.achieve_date
        WHERE di.account_no = :accountNo 
        AND di.today_date BETWEEN :startDate AND :endDate 
    ) AS daily_data
    GROUP BY target_date 
    ORDER BY target_date""",
            nativeQuery = true)
    List<Object[]> getDailyIntegratedData(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 주간 통합 데이터 쿼리
    @Query(value = """
    SELECT 
        MIN(target_date) AS date, 
        SUM(total_interest) AS total_interest, 
        AVG(avg_balance) AS avg_balance, 
        COUNT(DISTINCT target_date) AS total_days, 
        COALESCE(SUM(quest_count), 0) AS quest_count,
        COALESCE(SUM(total_points), 0) AS total_score
    FROM (
        SELECT 
            DATE(di.today_date) AS target_date,
            YEARWEEK(di.today_date, 1) AS week_group,
            di.today_interest AS total_interest,
            di.today_balance AS avg_balance,
            COALESCE(qa.quest_count, 0) AS quest_count,
            COALESCE(q.total_points, 0) AS total_points
        FROM daily_interest di 
        JOIN account a ON di.account_no = a.account_no 
        JOIN member m ON a.member_no = m.member_no 
        LEFT JOIN ( 
            SELECT 
                qa.member_no, 
                DATE(qa.achieve_datetime) AS achieve_date, 
                COUNT(*) AS quest_count
            FROM quest_achieve qa
            GROUP BY qa.member_no, DATE(qa.achieve_datetime) 
        ) qa ON m.member_no = qa.member_no 
            AND DATE(di.today_date) = qa.achieve_date 
        LEFT JOIN (
            SELECT 
                qa.member_no,
                DATE(qa.achieve_datetime) AS achieve_date,
                SUM(q.quest_point) AS total_points
            FROM quest_achieve qa
            JOIN quest q ON qa.quest_no = q.quest_no  -- 수정된 부분
            GROUP BY qa.member_no, DATE(qa.achieve_datetime)
        ) q ON m.member_no = q.member_no 
            AND DATE(di.today_date) = q.achieve_date
        WHERE di.account_no = :accountNo 
        AND di.today_date BETWEEN :startDate AND :endDate 
    ) AS weekly_data
    GROUP BY week_group
    ORDER BY date""",
            nativeQuery = true)
    List<Object[]> getWeeklyIntegratedData(
            @Param("accountNo") Long accountNo,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 월간 통합 데이터 쿼리
    @Query(value = """
    WITH monthly_data AS (
        SELECT 
            DATE(di.today_date) AS target_date,
            di.today_interest,
            di.today_balance,
            COALESCE(qa.quest_count, 0) AS quest_count,
            COALESCE(q.total_points, 0) AS total_points,
            LAST_DAY(di.today_date) AS last_day_of_month
        FROM daily_interest di 
        JOIN account a ON di.account_no = a.account_no 
        JOIN member m ON a.member_no = m.member_no 
        LEFT JOIN ( 
            SELECT 
                qa.member_no, 
                DATE(qa.achieve_datetime) AS achieve_date, 
                COUNT(*) AS quest_count
            FROM quest_achieve qa
            GROUP BY qa.member_no, DATE(qa.achieve_datetime) 
        ) qa ON m.member_no = qa.member_no 
            AND DATE(di.today_date) = qa.achieve_date 
        LEFT JOIN (
            SELECT 
                qa.member_no,
                DATE(qa.achieve_datetime) AS achieve_date,
                SUM(q.quest_point) AS total_points
            FROM quest_achieve qa
            JOIN quest q ON qa.quest_no = q.quest_no  -- 수정된 부분
            GROUP BY qa.member_no, DATE(qa.achieve_datetime)
        ) q ON m.member_no = q.member_no 
            AND DATE(di.today_date) = q.achieve_date
        WHERE di.account_no = :accountNo 
        AND di.today_date BETWEEN :startDate AND :endDate 
    )
    SELECT 
        last_day_of_month AS date,
        SUM(today_interest) AS total_interest,
        AVG(today_balance) AS avg_balance,
        COUNT(DISTINCT target_date) AS total_days,
        COALESCE(SUM(quest_count), 0) AS quest_count,
        COALESCE(SUM(total_points), 0) AS total_score
    FROM monthly_data
    GROUP BY last_day_of_month
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
            q.quest_content,
            q.quest_point,
            qa.achieve_datetime,
            q.quest_type
        FROM account a
        JOIN member m ON a.member_no = m.member_no
        JOIN quest_achieve qa ON m.member_no = qa.member_no
        JOIN quest q ON qa.quest_no = q.quest_no
        WHERE a.account_no = :accountNo
        AND DATE(qa.achieve_datetime) = :achieveDate
        ORDER BY qa.achieve_datetime DESC, q.quest_type ASC
        """, nativeQuery = true)
    List<Object[]> findQuestDetailsByDate(
            @Param("accountNo") Long accountNo,
            @Param("achieveDate") String achieveDate
    );
}