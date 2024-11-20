package org.hackathon.economy.report.domain.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@ToString
public class QuestDetailDTO {
    private String questName;
    private int questPoint;
    private LocalDateTime achieveDateTime;
    private int questType;

    public QuestDetailDTO(String questName, int questPoint, Timestamp achieveDateTime, int questType) {
        this.questName = questName;
        this.questPoint = questPoint;
        this.achieveDateTime = achieveDateTime != null ?
                achieveDateTime.toLocalDateTime() : null;
        this.questType = questType;
    }
}