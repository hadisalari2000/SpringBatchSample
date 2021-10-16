package com.examples.SpringBatchSample.repo;

import com.examples.SpringBatchSample.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
}
