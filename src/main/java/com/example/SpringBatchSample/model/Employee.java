package com.example.SpringBatchSample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    private String employeeId;
    private String fullName;
    private Integer age;
    private String calculateAge1;
    private String calculateAge2;
    private String email;


}
