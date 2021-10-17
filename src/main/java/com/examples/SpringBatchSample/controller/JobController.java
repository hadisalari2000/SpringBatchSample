package com.examples.SpringBatchSample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/run")
public class JobController {

    /*private final JobRunner jobRunner;

    public JobController(JobRunner jobRunner) {
        this.jobRunner = jobRunner;
    }*/


    @RequestMapping(value = "/jobs")
    public String runJob() {
        //jobRunner.runBatchJob();
        return "Job Employee From CSV To Database submitted successfully.";
    }
}
