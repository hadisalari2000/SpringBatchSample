package com.examples.SpringBatchSample.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class AccountJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("************************ BEFORE JOB "
                +jobExecution.getJobInstance().getJobName()
                +" *****************************");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("************************ AFTER JOB "
                +jobExecution.getJobInstance().getJobName()
                +" *****************************");
    }
}
