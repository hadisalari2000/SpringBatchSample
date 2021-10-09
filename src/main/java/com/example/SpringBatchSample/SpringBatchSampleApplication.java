package com.example.SpringBatchSample;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBatchSampleApplication.class, args);
    }
}
