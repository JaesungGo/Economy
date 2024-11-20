package org.hackathon.economy.quiz.repository;

import org.hackathon.economy.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // COALESCE: 횟수가 없을 때 0 처리, RAND(): 랜덤 셀렉트
    @Query(value = """
        SELECT q.*
        FROM quiz q
        LEFT JOIN (
            SELECT dq.quiz_pk, COUNT(*) AS quiz_count
            FROM daily_quiz dq
            GROUP BY dq.quiz_pk
        ) AS quiz_counts ON q.quiz_pk = quiz_counts.quiz_pk
        ORDER BY COALESCE(quiz_counts.quiz_count, 0) ASC, RAND()
        LIMIT 1
        """, nativeQuery = true)    List<Quiz> findQuizzesWithMinCumulated();

    @Query(value = "SELECT * FROM quiz WHERE quiz_pk = :quizPk", nativeQuery = true)
    Quiz findQuizById(@Param("quizPk") Long quizPk);
}