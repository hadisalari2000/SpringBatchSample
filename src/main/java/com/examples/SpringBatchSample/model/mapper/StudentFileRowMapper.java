package com.examples.SpringBatchSample.model.mapper;

import com.examples.SpringBatchSample.model.dto.StudentDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class StudentFileRowMapper implements FieldSetMapper<StudentDTO> {

    @Override
    public StudentDTO mapFieldSet(FieldSet fieldSet) {
        return StudentDTO.builder()
                .mail(fieldSet.readString("mail"))
                .firstName(fieldSet.readString("firstName"))
                .lastName(fieldSet.readString("lastName"))
                .age(fieldSet.readInt("age"))
                .build();
    }

}
