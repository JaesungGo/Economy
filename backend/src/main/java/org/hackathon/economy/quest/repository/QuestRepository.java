package org.hackathon.economy.quest.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hackathon.economy.quest.domain.Quest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class QuestRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<Quest> findByNo(Long questNo) {
        return Optional.ofNullable(em.find(Quest.class, questNo));
    }


}
