package org.hackathon.economy.quiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hackathon.economy.quest.domain.Quest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizPk;

    @Column(nullable = false)
    private String quizTitle;

    @Column(nullable = false)
    private String quizDesc;

    @Column(nullable = false)
    private Integer quizAnswer;

    @Column(nullable = false)
    private String quizType;

    @Column(nullable = false)
    private Integer quizCumulated = 0;

    @Column(nullable = false)
    private Timestamp quizCreatedAt;

    @Column(nullable = false)
    private Timestamp quizUpdatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_no")
    @JsonIgnore
    private Quest quest;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizOption> options = new ArrayList<>();
}