package org.hackathon.economy.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hackathon.economy.member.domain.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "ACCOUNT")
public class Account {

    @Id @GeneratedValue
    private Long accountNo;
    @Column(nullable = false)
    private Long accountBalance;
    @Column(nullable = false)
    private Double accountRate;
    @Column(nullable = false)
    private Date createDate;
    @Column(nullable = false)
    private Date updateDate;
    private Date closedDate;
    @Column(nullable = false)
    private Boolean accountStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyInterest> dailyInterests = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Interest interest;
}
