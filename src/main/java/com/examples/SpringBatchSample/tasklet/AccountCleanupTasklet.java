package com.examples.SpringBatchSample.tasklet;

import com.examples.SpringBatchSample.repo.AccountRepo;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountCleanupTasklet implements Tasklet {

    private final AccountRepo accountRepo;

    public AccountCleanupTasklet(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        accountRepo.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
