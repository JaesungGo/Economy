package org.hackathon.economy.quest.repository;

 import org.hackathon.economy.quest.domain.Quest;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.stereotype.Repository;

 import java.util.List;

 @Repository
 public interface QuestRepositoryInterface extends JpaRepository<Quest, Long> {
     List<Quest> findByIsActivateAndIsQr(boolean isActivate, boolean isQr);
 }
