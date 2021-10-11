package com.examples.SpringBatchSample.repo;

import com.examples.SpringBatchSample.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

    public Person findPersonById(Long id);
}
