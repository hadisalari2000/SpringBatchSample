package com.examples.SpringBatchSample.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student")
public class Student extends BaseEntity<Integer>{

    @Column(name = "fist_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "age",nullable = false)
    private Integer age;

}
