package com.examples.SpringBatchSample.model.entity;

import com.examples.SpringBatchSample.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccountTransaction extends BaseEntity<Long>{

    @Column(name="transaction_date",nullable = false)
    private Long transactionDate;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name="transaction_type",nullable = false)
    private TransactionType transactionType;

    @Column(name="account_id",nullable = false)
    private Long accountId;

    @ManyToOne()
    @JoinColumn(name="account_id",insertable = false,updatable = false)
    private Account account;
}
