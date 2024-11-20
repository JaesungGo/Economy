package org.hackathon.economy.quiz.repository;

import org.hackathon.economy.quiz.domain.DailyQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyQuizRepository extends JpaRepository<DailyQuiz, Long> {
    @Query(value = "SELECT quiz_pk FROM daily_quiz ORDER BY quiz_date_time DESC LIMIT 1", nativeQuery = true)
    Long findLatestQuizPk();
}