package org.hackathon.economy.quiz.repository;

import org.hackathon.economy.quiz.domain.DailyQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyQuizRepository extends JpaRepository<DailyQuiz, Long> {
    @Query(value = """
        SELECT q.quiz_pk
        FROM quiz q
        WHERE q.quiz_pk IN (
            SELECT dq.quiz_pk
            FROM daily_quiz dq
            GROUP BY dq.quiz_pk
            HAVING COUNT(*) = (
                SELECT MIN(quiz_count)
                FROM (
                    SELECT dq.quiz_pk, COUNT(*) AS quiz_count
                    FROM daily_quiz dq
                    GROUP BY dq.quiz_pk
                ) AS subquery
            )
        )
        UNION
        SELECT q.quiz_pk
        FROM quiz q
        WHERE NOT EXISTS (SELECT 1 FROM daily_quiz)
        ORDER BY RAND()
        LIMIT 1;
    """, nativeQuery = true)
    Long findLatestQuizPk();
}