package com.example.springbatch.job;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobRespositoryListener implements JobExecutionListener {


    @Autowired
    private JobRepository jobRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        JobParameters requestDate = new JobParametersBuilder()
                .addString("requestDate", "20230329").toJobParameters();

        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, requestDate);
    }
}
