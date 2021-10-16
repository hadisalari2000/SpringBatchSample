package com.examples.SpringBatchSample.tasklet;

import com.examples.SpringBatchSample.repo.StudentRepo;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class StudentCleanupTargetTasklet implements Tasklet {

    private final StudentRepo studentRepo;

    public StudentCleanupTargetTasklet(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        studentRepo.deleteAll();
        return RepeatStatus.FINISHED;
    }
}
