package com.examples.SpringBatchSample.writer;

import com.examples.SpringBatchSample.model.entity.Employee;
import com.examples.SpringBatchSample.repo.EmployeeRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDBWriter implements ItemWriter<Employee> {

    private final EmployeeRepo employeeRepo;

    public EmployeeDBWriter(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void write(List<? extends Employee> employees) {
        employeeRepo.saveAll(employees);
        System.out.println("inside writer " + employees);
    }
}
