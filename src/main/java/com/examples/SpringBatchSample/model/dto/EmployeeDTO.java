package com.examples.SpringBatchSample.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
