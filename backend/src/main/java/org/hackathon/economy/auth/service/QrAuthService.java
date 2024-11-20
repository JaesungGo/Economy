package org.hackathon.economy.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hackathon.economy.quest.domain.QuestAchieve;
import org.hackathon.economy.quest.repository.QuestAchieveRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class QrAuthService {
    private final QuestRepository questRepository;
    private final QuestAchieveRepository questAchieveRepository;


}
