package org.hackathon.economy.report.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackathon.economy.report.domain.dto.ReportResponse;
import org.hackathon.economy.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report")
    public ResponseEntity<ReportResponse> getReport(@RequestBody HashMap<String, Object> payload) {
        for (String result : payload.keySet()) {
            log.debug("POST /api/report payload value ----"+result+"--------");
        }
        String period = (String) payload.get("period");
        LocalDate startDate = LocalDate.parse((String) payload.get("startDate"));
        LocalDate endDate = LocalDate.parse((String) payload.get("endDate"));
        Long accountNo = Long.parseLong(payload.get("accountNo").toString());

        ReportResponse response = reportService.getReport(accountNo, period, startDate, endDate);
        return ResponseEntity.ok(response);
    }
}