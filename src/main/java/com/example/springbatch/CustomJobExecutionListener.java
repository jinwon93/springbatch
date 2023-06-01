package com.example.springbatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.util.Date;

public class CustomJobExecutionListener implements JobExecutionListener {



    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        jobExecution.getStartTime().getTime();
        jobExecution.getEndTime().getTime();
    }
}
