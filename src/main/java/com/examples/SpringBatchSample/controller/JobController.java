package com.examples.SpringBatchSample.controller;

import com.examples.SpringBatchSample.runner.JobRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*url: http://localhost:8080/run/employee*/

@RestController
@RequestMapping("/run")
public class JobController {

    private final JobRunner jobRunner;

    public JobController(JobRunner jobRunner) {
        this.jobRunner = jobRunner;
    }

    @RequestMapping(value = "/employee")
    public String runJob() {
        jobRunner.runBatchJob();
        return "Job Employee From CSV To Database submitted successfully.";
    }
}
