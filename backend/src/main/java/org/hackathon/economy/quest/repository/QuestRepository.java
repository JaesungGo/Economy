package org.hackathon.economy.quest.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hackathon.economy.quest.domain.Quest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public class QuestRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<Quest> findByNo(Long questNo) {
        return Optional.ofNullable(em.find(Quest.class, questNo));
    }


    public List<Quest> getActiveQuests() {
        return em.createQuery("select q from Quest q where q.isActive = true", Quest.class).getResultList();
    }

    public List<Quest> getActiveDailyQuests() {
        return em.createQuery("select q from Quest q where q.isActive = true and q.questType = 0", Quest.class).getResultList();
    }

    public List<Quest> getActiveWeeklyQuests() {
        return em.createQuery("select q from Quest q where q.isActive = true and q.questType = 1", Quest.class).getResultList();
    }

    public List<Quest> getActiveMonthlyQuests() {
        return em.createQuery("select q from Quest q where q.isActive = true and q.questType = 2", Quest.class).getResultList();
    }
}
