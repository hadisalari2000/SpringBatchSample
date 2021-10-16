package com.examples.SpringBatchSample.listener;

import com.examples.SpringBatchSample.model.entity.Account;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class WriterAccountStepListener implements ItemWriteListener<Account> {
    @Override
    public void beforeWrite(List<? extends Account> list) {
    }

    @Override
    public void afterWrite(List<? extends Account> list) {
        list.stream().forEach(System.out::println);
    }

    @Override
    public void onWriteError(Exception e, List<? extends Account> list) {
        System.out.println("On error while reading :"+e.getMessage());
    }
}
