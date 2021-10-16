package com.examples.SpringBatchSample.writer;

import com.examples.SpringBatchSample.model.entity.Student;
import com.examples.SpringBatchSample.repo.StudentRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDBWriter implements ItemWriter<Student> {

    private final StudentRepo studentRepo;

    public StudentDBWriter(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public void write(List<? extends Student> list) throws Exception {
        studentRepo.saveAll(list);
    }
}
