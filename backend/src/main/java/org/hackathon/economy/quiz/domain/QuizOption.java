package org.hackathon.economy.quiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "quiz_option")
public class QuizOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_pk", nullable = false)
    @JsonIgnore // 직렬화할 때, Quiz → QuizOption → Quiz → QuizOption 으로 순환이 발생합니다. ==> @JsonIgnore를 추가하여 순환 참조를 방지할 수 있습니다.
    private Quiz quiz;

    @Column(nullable = false)
    private Long optionId;

    @Column(nullable = false)
    private String optionText;

    @Column(nullable = false)
    private Boolean isCorrect;
}
