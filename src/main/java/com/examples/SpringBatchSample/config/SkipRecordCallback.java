package com.examples.SpringBatchSample.config;

import org.springframework.batch.item.file.LineCallbackHandler;

public class SkipRecordCallback implements LineCallbackHandler {
    @Override
    public void handleLine(String s) {
        System.out.println("####### first record skip ------> "+ s);
    }
}
