package com.examples.SpringBatchSample.config;

import com.examples.SpringBatchSample.utils.Constants;
import lombok.Data;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.Random;

@Data
public class CustomQuartzJob extends QuartzJobBean {

    private String jobName;
    private JobLauncher jobLauncher;
    private JobLocator jobLocator;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try
        {
            /*
            context.put(Constants.FILE_NAME_CONTEXT_KEY, "sampleData/employees.csv");
            context.put(Constants.FILE_NAME_PART,(new Random().ints(1, 1000000).findFirst().getAsInt()));
            */
            Job job = jobLocator.getJob(jobName);
            JobParameters params = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                    .addString(Constants.FILE_NAME_CONTEXT_KEY, "sampleData/employees.csv")
                    .addDate("date", new Date(), true)
                    .toJobParameters();

            jobLauncher.run(job, params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
