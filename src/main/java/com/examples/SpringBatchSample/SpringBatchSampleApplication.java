package com.examples.SpringBatchSample;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
//@EnableScheduling
public class SpringBatchSampleApplication {

    /*private final JobRunner jobRunner;

    public SpringBatchSampleApplication(JobRunner jobRunner) {
        this.jobRunner = jobRunner;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchSampleApplication.class, args);
    }

    //@Scheduled(cron = "0 */3 * * * *")
    /*public void perform() throws Exception{
        jobRunner.runBatchJob();
    }*/
}
