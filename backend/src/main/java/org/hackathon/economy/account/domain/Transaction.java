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
    @Column(name = "transaction_no")
    private Long transactionNo;
    @Column(name = "transaction_price", nullable = false)
    private Long transactionPrice;
    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;
    @Column(name = "transaction_type", nullable = false)
    private Integer transactionType;
    @Column(name = "transaction_balance", nullable = false)
    private Long transactionBalance;
}
