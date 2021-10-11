/*
package com.examples.SpringBatchSample.job;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class JobSkipPolicy implements SkipPolicy {

    private static final int MAX_SKIP_COUNT = 2;
    private static final int INVALID_TX_AMOUNT_LIMIT = -1000;

    @Override
    public boolean shouldSkip(Throwable throwable, int failedCount) throws SkipLimitExceededException {
        if (throwable instanceof MissingUsernameException && failedCount < MAX_SKIP_COUNT) {
            return true;
        }

        if (throwable instanceof NegativeAmountException && failedCount < MAX_SKIP_COUNT ) {
            NegativeAmountException ex = (NegativeAmountException) throwable;
            if(ex.getAmount() < INVALID_TX_AMOUNT_LIMIT) {
                return false;
            } else {
                return true;
            }
        }

        return false;
    }
}
*/
