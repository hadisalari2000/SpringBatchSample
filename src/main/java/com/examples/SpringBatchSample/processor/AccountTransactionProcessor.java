package com.examples.SpringBatchSample.processor;

import com.examples.SpringBatchSample.model.entity.Account;
import com.examples.SpringBatchSample.model.entity.AccountTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountTransactionProcessor implements ItemProcessor<Account, List<AccountTransaction>> {

    @Override
    public List<AccountTransaction> process(Account account) throws Exception {
        return null;
    }
}
