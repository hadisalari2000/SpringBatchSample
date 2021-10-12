package com.examples.SpringBatchSample.listener;

import com.examples.SpringBatchSample.dto.AccountDTO;
import com.examples.SpringBatchSample.model.entity.Account;
import org.springframework.batch.core.ItemProcessListener;

public class ProcessorAccountStepListener implements ItemProcessListener<AccountDTO, Account> {

    @Override
    public void beforeProcess(AccountDTO accountDTO) {
    }

    @Override
    public void afterProcess(AccountDTO accountDTO, Account account) {
        System.out.println("AFTER Process Account : ["+account.toString()+"]");
    }

    @Override
    public void onProcessError(AccountDTO accountDTO, Exception e) {
        System.out.println("On Processor Error : "+ e.getMessage());
    }
}
