package com.examples.SpringBatchSample.processor;

import com.examples.SpringBatchSample.dto.EmployeeDTO;
import com.examples.SpringBatchSample.model.entity.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDTOProcessor implements ItemProcessor<Employee, EmployeeDTO> {

    @Override
    public EmployeeDTO process(Employee employee) {
        return EmployeeDTO.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFullName().split(" - ")[0])
                .lastName(employee.getFullName().split(" - ")[1])
                .email(employee.getEmail())
                .age(employee.getAge())
                .build();
    }
}
