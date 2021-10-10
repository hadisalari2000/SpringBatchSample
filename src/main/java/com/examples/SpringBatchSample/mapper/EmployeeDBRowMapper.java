package com.examples.SpringBatchSample.mapper;

import com.examples.SpringBatchSample.model.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeDBRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Employee.builder()
                    .employeeId(rs.getString("employee_id"))
                    .fullName(rs.getString("full_name"))
                    .calculateAge1(rs.getString("calculate_age1"))
                    .calculateAge2(rs.getString("calculate_age2"))
                    .email(rs.getString("email"))
                    .age(rs.getInt("age"))
                    .build();
    }
}
