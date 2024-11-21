package org.hackathon.economy.quest.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackathon.economy.quest.service.QuestAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quests")
@RequiredArgsConstructor
public class QuestController {
    private final QuestAuthService questAuthService;

    @PostMapping("/process")
    public ResponseEntity<String> processQuestAchievements(@RequestBody Long memberNo) {
        try {
            questAuthService.processQuestAchievements(memberNo);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("error" + e.getMessage());
        }
    }
}