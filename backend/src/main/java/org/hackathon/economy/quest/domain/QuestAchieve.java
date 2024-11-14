package org.hackathon.economy.quest.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hackathon.economy.member.domain.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "QUEST_ACHEIVE")
public class QuestAchieve {

    @Id @GeneratedValue
    private Long achieveNo;
    private Date achieveDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_no")
    private Quest quest;

}
