package com.examples.SpringBatchSample.job;

import com.examples.SpringBatchSample.tasklet.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultipleJobs {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public MultipleJobs(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Qualifier(value = "jobOne")
    @Bean
    public Job jobOne() throws Exception {
        return this.jobBuilderFactory.get("jobOne")
                .start(stepOneForJobOne())
                .next(stepTwoForJobOne())
                .build();
    }

    @Bean
    public Step stepOneForJobOne() {
      return this.stepBuilderFactory.get("stepOneForJobOne")
                .tasklet(new TaskletOne())
                .build();
    }

    @Bean
    public Step stepTwoForJobOne() {
       return this.stepBuilderFactory.get("stepOneForJobOne")
                .tasklet(new TaskletTwo())
                .build();
    }


    @Qualifier(value = "jobTwo")
    @Bean
    public Job jobTwo() throws Exception {
        return this.jobBuilderFactory.get("jobTwo")
                .start(stepOneForJobThree())
                .next(stepTwoForJobFour())
                .build();
    }

    @Bean
    public Step stepOneForJobThree() {
        return this.stepBuilderFactory.get("stepOneForJobThree")
                .tasklet(new TaskletThree())
                .build();
    }

    @Bean
    public Step stepTwoForJobFour() {
        return this.stepBuilderFactory.get("stepTwoForJobFour")
                .tasklet(new TaskletFour())
                .build();
    }
}
