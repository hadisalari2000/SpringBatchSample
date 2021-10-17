package com.examples.SpringBatchSample.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskletOne implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("############# Job 1 ------> Step 1 --------> Start");
        System.out.println("############# Job 1 ------> Step 1 --------> End");
        return RepeatStatus.FINISHED;
    }
}
