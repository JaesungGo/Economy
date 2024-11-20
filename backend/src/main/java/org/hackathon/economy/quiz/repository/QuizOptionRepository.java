package org.hackathon.economy.quiz.repository;

import org.hackathon.economy.quiz.domain.Quiz;
import org.hackathon.economy.quiz.domain.QuizOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizOptionRepository extends JpaRepository<QuizOption, Long> {
    // 네이티브 SQL 쿼리 대신 JPA JPQL 쿼리를 사용 하는 방식
    @Query("SELECT qo FROM QuizOption qo WHERE qo.quiz = :quiz")
    List<QuizOption> findQuizOptionsByQuiz(@Param("quiz") Quiz quiz);
}
