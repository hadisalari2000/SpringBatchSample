package com.examples.SpringBatchSample.tasklet;

import com.examples.SpringBatchSample.repo.EmployeeRepo;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCleanupTasklet implements Tasklet {

    private final EmployeeRepo employeeRepo;

    public EmployeeCleanupTasklet(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        employeeRepo.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
