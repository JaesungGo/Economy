package org.hackathon.economy.quest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "QUEST")
public class Quest {

    @Id
    @GeneratedValue
    @Column(name = "quest_no")
    private Long questNo;
    @Column(name = "quest_type", nullable = false)
    private Integer questType;
    @Column(name = "quest_content", nullable = false)
    private String questContent;
    @Column(name = "quest_point", nullable = false)
    private Integer questPoint;
    @Column(name = "quest_count", nullable = false)
    private Integer questCount;
    @Column(name = "create_table_datetime", nullable = false)
    private Date createTableDatetime;
}
