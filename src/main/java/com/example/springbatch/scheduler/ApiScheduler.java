package com.example.springbatch.scheduler;


import lombok.SneakyThrows;
import org.hibernate.type.JavaObjectType;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ApiScheduler  extends QuartzJobBean {


    @Qualifier("childJob")
    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;


    @SneakyThrows
    @Override
    protected  void  executeInternal(JobExecutionContext context) throws JobExecutionException {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("io" , new Date().getTime())
                .toJobParameters();


        jobLauncher.run(job , jobParameters);


    }
}
