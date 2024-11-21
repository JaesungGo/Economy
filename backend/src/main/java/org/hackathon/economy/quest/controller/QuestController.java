package org.hackathon.economy.quest.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hackathon.economy.account.domain.Account;
import org.hackathon.economy.account.service.AccountService;
import org.hackathon.economy.member.domain.Member;
import org.hackathon.economy.member.service.AuthenticationService;
import org.hackathon.economy.quest.domain.Quest;
import org.hackathon.economy.quest.domain.QuestAchieve;
import org.hackathon.economy.quest.service.QuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quest")
public class QuestController {

    private final QuestService questService;
    private final AccountService accountService;

    // 진행 중인 퀘스트 전체 조회
    @GetMapping("/active")
    public ResponseEntity<List<Quest>> getActiveQuests() {
        return questService.getActiveQuests();
    }

    // 진행 중인 일일 퀘스트 조회
    @GetMapping("/active/daily")
    public ResponseEntity<List<Quest>> getActiveDailyQuests() {
        return questService.getActiveDailyQuests();
    }

    // 진행 중인 주간 퀘스트 조회
    @GetMapping("/active/weekly")
    public ResponseEntity<List<Quest>> getActiveWeelyQuests() {
        return questService.getActiveWeeklyQuests();
    }


    // 진행 중인 월간 퀘스트 조회
    @GetMapping("/active/monthly")
    public ResponseEntity<List<Quest>> getActiveMonthlyQuests() {
        return questService.getActiveMonthlyQuests();
    }
}
