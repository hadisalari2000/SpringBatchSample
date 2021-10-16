package com.examples.SpringBatchSample.processor;

import com.examples.SpringBatchSample.model.dto.StudentDTO;
import com.examples.SpringBatchSample.model.entity.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StudentProcessor implements ItemProcessor<StudentDTO, Student> {

    @Override
    public Student process(StudentDTO dto) throws Exception {
        return Student.builder()
                .email(dto.getMail())
                .age(dto.getAge())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }
}
