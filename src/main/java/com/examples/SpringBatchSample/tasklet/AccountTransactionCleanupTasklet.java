package com.examples.SpringBatchSample.tasklet;

import com.examples.SpringBatchSample.repo.AccountTransactionRepo;
import com.examples.SpringBatchSample.repo.StudentRepo;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountTransactionCleanupTasklet implements Tasklet {

    private final AccountTransactionRepo accountTransactionRepo;

    public AccountTransactionCleanupTasklet(AccountTransactionRepo accountTransactionRepo) {
        this.accountTransactionRepo = accountTransactionRepo;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        accountTransactionRepo.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
