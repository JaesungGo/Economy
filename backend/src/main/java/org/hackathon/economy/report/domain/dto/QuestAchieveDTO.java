package org.hackathon.economy.report.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class QuestAchieveDTO {
    private Date achieveDate;
    private Long questCount;

    public QuestAchieveDTO(Date achieveDate, Long questCount) {
        this.achieveDate = achieveDate;
        this.questCount = questCount;
    }
}
