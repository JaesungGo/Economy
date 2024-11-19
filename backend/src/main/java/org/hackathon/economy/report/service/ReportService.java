package org.hackathon.economy.report.service;

import lombok.RequiredArgsConstructor;
import org.hackathon.economy.account.domain.DailyInterest;
import org.hackathon.economy.report.domain.dto.*;
import org.hackathon.economy.report.repository.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportResponse getQuest(Long accountNo, String questDate) {
        ReportResponse response = new ReportResponse();

        if (questDate != null && !questDate.isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(questDate);
                List<QuestDetailDTO> questDetails = getQuestDetails(accountNo, date);
                response.setQuestDetails(questDetails);

                System.out.println("Querying for date: " + date);
                System.out.println("Found quest details: " + questDetails.size());
                questDetails.forEach(detail ->
                        System.out.println("Quest: " + detail.getQuestName() +
                                ", Achieved at: " + detail.getAchieveDateTime()));
            } catch (Exception e) {
                System.err.println("Error processing quest details: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return response;
    }

    public ReportResponse getReport(Long accountNo, String period, LocalDate startDate, LocalDate endDate, Integer page) {
        ReportResponse response = new ReportResponse();

        // 멤버 등급 및 포인트 추가
        List<Object[]> memberGradeAndScore = reportRepository.findMemberGradeAndScoreByAccountNo(accountNo);
        response.setMemberData(memberGradeAndScore);

        // 페이징 설정
        Pageable pageable = PageRequest.of(page != null ? page : 0, 20, Sort.by("todayDate").descending());

        // 전체 이자 내역 페이징 조회
        Page<DailyInterest> interestHistoryPage = reportRepository.findAllInterestHistory(
                accountNo, startDate, endDate, pageable
        );

        List<DailyInterestDTO> interestHistory = interestHistoryPage.getContent().stream()
                .map(DailyInterestDTO::from)
                .collect(Collectors.toList());

        // 통합 데이터 조회
        List<Object[]> integratedData;
        Map<YearWeek, WeeklyAggregation> aggregations = new HashMap<>();

        switch (period) {
            case "weekly":
                integratedData = reportRepository.getWeeklyIntegratedData(accountNo, startDate, endDate);
                processWeeklyData(integratedData, aggregations);
                break;
            case "monthly":
                integratedData = reportRepository.getMonthlyIntegratedData(accountNo, startDate, endDate);
                processMonthlyData(integratedData, response);
                break;
            default: // daily
                integratedData = reportRepository.getDailyIntegratedData(accountNo, startDate, endDate);
                processDailyData(integratedData, response);
                break;
        }

        // 각 차트 데이터 설정
        setChartData(integratedData, response, period);

        // 페이징 정보 설정
        response.setInterestHistory(interestHistory);
        response.setTotalPages(interestHistoryPage.getTotalPages());
        response.setCurrentPage(interestHistoryPage.getNumber());
        response.setTotalItems(interestHistoryPage.getTotalElements());

        return response;
    }

    private void setChartData(List<Object[]> integratedData, ReportResponse response, String period) {
        List<ChartDataDTO> chartData = integratedData.stream()
                .map(data -> new ChartDataDTO(
                        ((java.sql.Date) data[0]).toLocalDate(),
                        ((Number) data[1]).longValue(),  // total_interest
                        ((Number) data[2]).doubleValue(), // avg_balance
                        ((Number) data[3]).longValue()    // total_days
                ))
                .collect(Collectors.toList());

        List<ChartDataDTO> questData = integratedData.stream()
                .map(data -> new ChartDataDTO(
                        ((java.sql.Date) data[0]).toLocalDate(),
                        ((Number) data[4]).longValue(),  // quest_count
                        0.0,
                        0L
                ))
                .collect(Collectors.toList());

        List<ChartDataDTO> scoreData = integratedData.stream()
                .map(data -> new ChartDataDTO(
                        ((java.sql.Date) data[0]).toLocalDate(),
                        ((Number) data[5]).longValue(),  // total_score
                        0.0,
                        0L
                ))
                .collect(Collectors.toList());

        response.setInterestData(chartData.stream()
                .map(data -> ReportResponse.ChartData.from(data, ChartDataDTO::getTotalInterest))
                .collect(Collectors.toList()));

        response.setBalanceData(chartData.stream()
                .map(data -> ReportResponse.ChartData.from(data, d -> Math.round(d.getAverageBalance())))
                .collect(Collectors.toList()));

        response.setQuestData(questData.stream()
                .map(data -> ReportResponse.ChartData.from(data, ChartDataDTO::getTotalInterest))
                .collect(Collectors.toList()));

        response.setScoreData(scoreData.stream()
                .map(data -> ReportResponse.ChartData.from(data, ChartDataDTO::getTotalInterest))
                .collect(Collectors.toList()));
    }

    private void processDailyData(List<Object[]> integratedData, ReportResponse response) {
        // 일간 데이터는 추가 처리 없이 바로 차트 데이터로 변환
    }

    private void processWeeklyData(List<Object[]> integratedData, Map<YearWeek, WeeklyAggregation> aggregations) {
        integratedData.forEach(data -> {
            LocalDate date = ((java.sql.Date) data[0]).toLocalDate();
            YearWeek yearWeek = new YearWeek(date);

            aggregations.computeIfAbsent(yearWeek, k -> new WeeklyAggregation())
                    .addData(
                            ((Number) data[1]).longValue(),    // interest
                            ((Number) data[2]).doubleValue(),  // balance
                            ((Number) data[4]).longValue(),    // quest count
                            ((Number) data[5]).longValue()     // score
                    );
        });
    }

    private void processMonthlyData(List<Object[]> integratedData, ReportResponse response) {
        // 월간 데이터는 LAST_DAY로 그룹화되어 있으므로 추가 처리 없이 바로 변환
    }

    // 특정 날짜의 퀘스트 상세 내역 조회
    public List<QuestDetailDTO> getQuestDetails(Long accountNo, LocalDate date) {
        try {
            // LocalDate를 String으로 변환
            String formattedDate = date.format(DateTimeFormatter.ISO_DATE);

            // 쿼리 실행 전 파라미터 로깅
            System.out.println("Querying for accountNo: " + accountNo + ", date: " + formattedDate);

            List<Object[]> questDetails = reportRepository.findQuestDetailsByDate(accountNo, formattedDate);

            // 결과 데이터 로깅
            System.out.println("Found " + questDetails.size() + " quest details");

            return questDetails.stream()
                    .map(data -> {
                        try {
                            String questName = (String) data[0];
                            Integer questPoint = ((Number) data[1]).intValue();
                            Timestamp achieveDateTime = (Timestamp) data[2];
                            Integer questType = ((Number) data[3]).intValue();

                            return new QuestDetailDTO(
                                    questName,
                                    questPoint,
                                    achieveDateTime,
                                    questType
                            );
                        } catch (Exception e) {
                            System.err.println("Error mapping quest detail: " + e.getMessage());
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error in getQuestDetails: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 내부 클래스들
    private static class YearWeek {
        private final LocalDate startDate;
        private final int year;
        private final int week;

        public YearWeek(LocalDate date) {
            this.startDate = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            this.year = date.getYear();
            this.week = date.get(weekFields.weekOfWeekBasedYear());
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            YearWeek yearWeek = (YearWeek) o;
            return year == yearWeek.year && week == yearWeek.week;
        }

        @Override
        public int hashCode() {
            return Objects.hash(year, week);
        }
    }

    private static class WeeklyAggregation {
        private long totalInterest = 0;
        private double totalBalance = 0.0;
        private long totalQuests = 0;
        private long totalScore = 0;
        private long days = 0;

        public void addData(long interest, double balance, long quests, long score) {
            this.totalInterest += interest;
            this.totalBalance += balance;
            this.totalQuests += quests;
            this.totalScore += score;
            this.days++;
        }

        public long getTotalInterest() {
            return totalInterest;
        }

        public double getAverageBalance() {
            return days > 0 ? totalBalance / days : 0.0;
        }

        public long getTotalQuests() {
            return totalQuests;
        }

        public long getTotalScore() {
            return totalScore;
        }

        public long getDays() {
            return days;
        }
    }
}