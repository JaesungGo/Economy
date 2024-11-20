package org.hackathon.economy.quiz.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "daily_quiz")
public class DailyQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyQuizId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_pk", nullable = false)
    private Quiz quiz;

    @Column(nullable = false)
//    private LocalDate quizDate;
    private LocalDateTime quizDateTime;

}
