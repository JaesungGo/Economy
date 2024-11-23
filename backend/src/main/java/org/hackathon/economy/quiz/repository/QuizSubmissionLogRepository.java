package org.hackathon.economy.quiz.repository;

import org.hackathon.economy.quiz.domain.QuizSubmissionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface QuizSubmissionLogRepository extends JpaRepository<QuizSubmissionLog, Long> {
    long countByMemberNoAndIsCorrectAndSubmissionTimeBetween(
            Long memberNo, Boolean isCorrect, Timestamp startTime, Timestamp endTime
    );
}
