package org.hackathon.economy.quiz.repository;

import org.hackathon.economy.quiz.domain.QuizSubmissionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSubmissionLogRepository extends JpaRepository<QuizSubmissionLog, Long> {
}
