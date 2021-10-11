package com.examples.SpringBatchSample.model.mapper;

import com.examples.SpringBatchSample.dto.AccountDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class AccountFileRowMapper implements FieldSetMapper<AccountDTO> {

    @Override
    public AccountDTO mapFieldSet(FieldSet fieldSet) {
        return AccountDTO.builder()
                .part1(fieldSet.readString("part1"))
                .part2(fieldSet.readString("part2"))
                .part4(fieldSet.readString("part4"))
                .build();
    }

}
