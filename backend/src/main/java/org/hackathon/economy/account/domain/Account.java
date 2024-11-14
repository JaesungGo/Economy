package org.hackathon.economy.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "ACCOUNT")
public class Account {

    @Id @GeneratedValue
    @Column(name = "account_no")
    private Long accountNo;
    @Column(name = "account_balance", nullable = false)
    private Long accountBalance;
    @Column(name = "account_rate", nullable = false)
    private Double accountRate;
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Column(name = "update_date", nullable = false)
    private Date updateDate;
    @Column(name = "closed_date")
    private Date closedDate;
    @Column(name = "account_status", nullable = false)
    private Boolean accountStatus;
}
