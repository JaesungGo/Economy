package org.hackathon.economy.account.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hackathon.economy.member.domain.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @JsonBackReference
    private Member member;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyInterest> dailyInterests = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "interest_no") // 외래 키는 Account 테이블에 위치
    private Interest interest;

    // 도메인 메서드: 잔액 추가
    public Long deposit(Long amount) {
        this.accountBalance += amount;
        this.updateDate = new Date();

        return this.accountBalance;
    }

    // 도메인 메서드: 잔액 감소
    public Long withdraw(Long amount) {

        if (this.accountBalance < amount) {
            return null;
        }

        this.accountBalance -= amount;
        this.updateDate = new Date();

        return this.accountBalance;
    }

    /* 연관관계 편의 메서드 */
    public void setMember(Member member) {
        this.member = member;
        if (member != null && member.getAccount() != this) {
            member.setAccount(this);
        }
    }
}
