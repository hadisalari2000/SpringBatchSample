package com.example.SpringBatchSample.mapper;

import com.example.SpringBatchSample.dto.EmployeeDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class EmployeeFileRowMapper implements FieldSetMapper<EmployeeDTO> {

    @Override
    public EmployeeDTO mapFieldSet(FieldSet fieldSet) {
        return EmployeeDTO.builder()
                .employeeId(fieldSet.readString("employeeId"))
                .firstName(fieldSet.readString("firstName"))
                .lastName(fieldSet.readString("lastName"))
                .email(fieldSet.readString("email"))
                .age(fieldSet.readInt("age"))
                .build();
    }

}
