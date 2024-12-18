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
    private Long questNo;
    @Column(nullable = false)
    private Integer questType; // "0": 일간 , "1": 주간, "2": 월간
    @Column(nullable = false)
    private String questContent;
    @Column(nullable = false)
    private Integer questPoint;
    @Column(nullable = false)
    private Integer questCount;
    @Column(nullable = false)
    private Date createTableDatetime;
    @Column(nullable = false)
    private Boolean isQr;
    @Column(nullable = false)
    private Boolean isActive;
    @Column(nullable = false)
    private Integer questFrequency;
}
