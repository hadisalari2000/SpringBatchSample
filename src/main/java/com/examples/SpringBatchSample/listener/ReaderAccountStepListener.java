package com.examples.SpringBatchSample.listener;

import com.examples.SpringBatchSample.dto.AccountDTO;
import org.springframework.batch.core.ItemReadListener;

public class ReaderAccountStepListener implements ItemReadListener<AccountDTO> {

    @Override
    public void beforeRead() {
    }

    @Override
    public void afterRead(AccountDTO accountDTO) {
        System.out.println("After Read AccountDTO ["+accountDTO.toString()+"]");
    }

    @Override
    public void onReadError(Exception e) {
        System.out.println("On error while reading :"+e.getMessage() );
    }
}
