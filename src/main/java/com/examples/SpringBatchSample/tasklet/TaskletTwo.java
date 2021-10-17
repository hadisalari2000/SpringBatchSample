package com.examples.SpringBatchSample.tasklet;

import com.examples.SpringBatchSample.utils.Constants;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskletTwo implements Tasklet {

    public TaskletTwo() {
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        System.out.println("############# Job 1 ------> Step 2 --------> Start"+ "++++++++++++++++");
        System.out.println("############# Job 1 ------> Step 2 --------> End"+ "++++++++++++++++");
        return RepeatStatus.FINISHED;
    }
}
