package com.examples.SpringBatchSample.writer;

import com.examples.SpringBatchSample.model.entity.AccountTransaction;
import com.examples.SpringBatchSample.repo.AccountRepo;
import com.examples.SpringBatchSample.repo.AccountTransactionRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountTransactionDBWriter implements ItemWriter<List<AccountTransaction>> {

    private final AccountTransactionRepo accountTransactionRepo;

    public AccountTransactionDBWriter(AccountTransactionRepo accountTransactionRepo) {
        this.accountTransactionRepo = accountTransactionRepo;
    }

    @Override
    public void write(List<? extends List<AccountTransaction>> list) throws Exception {
        for (List<AccountTransaction> transactions:list ) {
            accountTransactionRepo.saveAll(transactions);
            System.out.println("inside writer " + transactions);
        }

    }
}
