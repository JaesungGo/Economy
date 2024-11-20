package org.hackathon.economy.quiz.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizSubmissionDto {
    private Long memberNo;
    private Long quizPk;
    private Integer userAnswer;
}