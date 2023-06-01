package com.example.springbatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class CustomAnotationJobExecutionListener {


    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {

    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        jobExecution.getStartTime().getTime();
        jobExecution.getEndTime().getTime();
    }
}
