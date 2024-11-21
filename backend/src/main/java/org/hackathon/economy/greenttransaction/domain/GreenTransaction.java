package org.hackathon.economy.greenttransaction.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "green_transactions")
public class GreenTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionNo;
    private LocalDateTime transactionTime;
    private String serviceProvider;
    private BigDecimal transactionAmount;
    private String transactionCategory;
}