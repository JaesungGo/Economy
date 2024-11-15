package org.hackathon.economy.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hackathon.economy.account.domain.Account;
import org.hackathon.economy.quest.domain.Quest;
import org.hackathon.economy.quest.domain.QuestAchieve;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "MEMBER")
public class Member {

    @Id @GeneratedValue
    private Long memberNo;
    @Column(nullable = false)
    private String memberName;
    @Column(nullable = false)
    private Integer memberGrade;
    @Column(nullable = false)
    private Long memberPoint;
    @Column(nullable = false)
    private String memberEmail;
    @Column(nullable = false)
    private String memberPassword;
    @Column(nullable = false)
    private Date createDate;
    @Column(nullable = false)
    private Date updatedDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestAchieve> questAchieves = new ArrayList<>();
}
