package com.examples.SpringBatchSample.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String mail;
    private String firstName;
    private String lastName;
    private Integer age;
}
