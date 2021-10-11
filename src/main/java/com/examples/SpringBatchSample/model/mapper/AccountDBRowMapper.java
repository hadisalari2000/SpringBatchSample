package com.examples.SpringBatchSample.model.mapper;

import com.examples.SpringBatchSample.model.entity.Account;
import com.examples.SpringBatchSample.model.entity.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AccountDBRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Account.builder()
                    .id(rs.getLong("id"))
                    .accountNumber(rs.getString("account_number"))
                    .personId(rs.getLong("person_id"))
                    .build();
    }
}
