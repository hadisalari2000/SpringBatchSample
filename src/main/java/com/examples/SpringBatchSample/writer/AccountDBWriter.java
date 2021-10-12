package com.examples.SpringBatchSample.writer;

import com.examples.SpringBatchSample.model.entity.Account;
import com.examples.SpringBatchSample.repo.AccountRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDBWriter implements ItemWriter<Account> {

    private final AccountRepo accountRepo;

    public AccountDBWriter(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public void write(List<? extends Account> accounts) {
        accountRepo.saveAll(accounts);
    }
}
