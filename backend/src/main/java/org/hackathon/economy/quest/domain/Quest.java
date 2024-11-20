package org.hackathon.economy.quest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hackathon.economy.member.domain.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "QUEST")
public class Quest {

    @Id
    @GeneratedValue
    private Long questNo;
    @Column(nullable = false)
    private Integer questType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questAchieve_no")
    private QuestAchieve questAchieve;
}
