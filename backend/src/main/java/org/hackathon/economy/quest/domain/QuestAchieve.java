package org.hackathon.economy.quest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "QUEST_ACHEIVE")
public class QuestAchieve {

    @Id @GeneratedValue
    @Column(name = "achieve_no")
    private Long achieveNo;
    @Column(name = "achieve_datetime")
    private Date achieveDateTime;
}
