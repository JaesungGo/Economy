package org.hackathon.economy.report.service;

import lombok.RequiredArgsConstructor;
import org.hackathon.economy.account.domain.DailyInterest;
import org.hackathon.economy.report.domain.dto.*;
import org.hackathon.economy.report.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportResponse getReport(Long accountNo, String period, LocalDate startDate, LocalDate endDate) {
        ReportResponse response = new ReportResponse();

        // 기간별로 적절한 차트 데이터 조회
        List<ChartDataDTO> chartData;
        switch (period) {
            case "weekly":
                chartData = reportRepository.getWeeklyChartData(accountNo, startDate, endDate)
                        .stream()
                        .map(ChartDataDTO::new)
                        .collect(Collectors.toList());
                break;
            case "monthly":
                chartData = reportRepository.getMonthlyChartData(accountNo, startDate, endDate)
                        .stream()
                        .map(ChartDataDTO::new)
                        .collect(Collectors.toList());
                break;
            default: // daily
                chartData = reportRepository.getDailyChartData(accountNo, startDate, endDate);
        }

        // 이자 데이터 설정
        response.setInterestData(chartData.stream()
                .map(data -> ReportResponse.ChartData.from(data, ChartDataDTO::getTotalInterest))
                .collect(Collectors.toList()));

        // 잔액 데이터 설정
        response.setBalanceData(chartData.stream()
                .map(data -> ReportResponse.ChartData.from(data, d -> Math.round(d.getAverageBalance())))
                .collect(Collectors.toList()));

        // 퀘스트 데이터 설정
        List<Long> questCounts = reportRepository.getQuestCounts(accountNo, startDate, endDate);
        List<ReportResponse.ChartData> questChartData = new ArrayList<>();

        LocalDate currentDate = startDate;
        int index = 0;
        while (!currentDate.isAfter(endDate)) {
            ReportResponse.ChartData cd = new ReportResponse.ChartData();
            cd.setDate(currentDate.toString());
            cd.setValue(index < questCounts.size() ? questCounts.get(index) : 0L);
            questChartData.add(cd);
            currentDate = currentDate.plusDays(1);
            index++;
        }
        response.setQuestData(questChartData);

        // 이자 내역 조회 - DTO로 변환
        List<DailyInterest> dailyInterests = reportRepository.findDailyData(accountNo, startDate, endDate);
        response.setInterestHistory(dailyInterests.stream()
                .map(DailyInterestDTO::from)
                .collect(Collectors.toList()));

        return response;
    }
}