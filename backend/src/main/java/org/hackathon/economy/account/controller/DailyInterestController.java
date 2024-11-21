package org.hackathon.economy.account.controller;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hackathon.economy.account.domain.DailyInterest;
import org.hackathon.economy.account.domain.DailyInterestUtil;
import org.hackathon.economy.account.service.DailyInterestService;
import org.hackathon.economy.member.domain.Member;
import org.hackathon.economy.member.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dailyinterest")
public class DailyInterestController {

    private final DailyInterestService dailyInterestService;
    private final AuthenticationService authenticationService;

    // 전체 내역 조회
    @GetMapping("/")
    public ResponseEntity<List<DailyInterest>> getAll(HttpSession session) {
        try {
            Member member = authenticationService.getAuthenticatedMember(session);
            List<DailyInterest> dailyInterests = dailyInterestService.getAll(member);
            return ResponseEntity.ok(dailyInterests);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // 누적이자 조회
    @GetMapping("/total")
    public ResponseEntity<Long> getTotal(HttpSession session) {
        try {
            Member member = authenticationService.getAuthenticatedMember(session);
            Long totalInterest = dailyInterestService.getTotal(member);
            return ResponseEntity.ok(totalInterest);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // 매월 말일 월별 누적이자 조회
    @GetMapping("/monthly")
    public ResponseEntity<List<DailyInterestUtil>> getMonthly(HttpSession session) {
        try {
            Member member = authenticationService.getAuthenticatedMember(session);
            List<DailyInterestUtil> monthlyInterest = dailyInterestService.getMonthly(member);
            return ResponseEntity.ok(monthlyInterest);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}


