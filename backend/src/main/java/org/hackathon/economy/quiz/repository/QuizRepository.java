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
            SELECT * FROM quiz
            """, nativeQuery = true)
    List<Quiz> findQuizzesWithMinCumulated();
    @Query(value = "SELECT * FROM quiz WHERE quiz_pk = :quizPk", nativeQuery = true)
    Quiz findQuizById(@Param("quizPk") Long quizPk);
}