package org.hackathon.economy.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "TRANSACTION")
public class Transaction {

    @Id @GeneratedValue
    private Long transactionNo;
    @Column(nullable = false)
    private Long transactionPrice;
    @Column(nullable = false)
    private Date transactionDate;
    @Column(nullable = false)
    private Integer transactionType;
    @Column(nullable = false)
    private Long transactionBalance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_no")
    private Account account;
}
