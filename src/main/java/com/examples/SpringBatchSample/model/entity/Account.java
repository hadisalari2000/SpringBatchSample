package com.examples.SpringBatchSample.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account extends BaseEntity<Long>{

    @Column(name="account_number",nullable = false)
    private String accountNumber;

    @Column(name="person_id",nullable = false)
    private Long personId;

    @ManyToOne()
    @JoinColumn(name="person_id",insertable = false,updatable = false)
    private Person person;
}
