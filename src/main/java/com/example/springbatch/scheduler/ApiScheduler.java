package com.example.springbatch.scheduler;


import lombok.SneakyThrows;
import org.hibernate.type.JavaObjectType;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiScheduler  extends QuartzJobBean {


    @Qualifier("childJob")
    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;


    @Autowired
    private JobExplorer jobExplorer;

    @SneakyThrows
    @Override
    protected  void  executeInternal(JobExecutionContext context) throws JobExecutionException {

        String requestDate = (String) context.getJobDetail().getJobDataMap().get("requestDate");


        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("io" , new Date().getTime())
                .toJobParameters();

        int jobInstanceCount = jobExplorer.getJobInstanceCount(job.getName());
        List<JobInstance> jobInstances = jobExplorer.getJobInstances(job.getName(), 0, jobInstanceCount);

        if (jobInstances.size() > 0) {
            for (JobInstance jobInstance : jobInstances) {
                List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
                List<JobExecution> requestList = jobExecutions.stream().filter(jobExecution -> jobExecution.getJobParameters().getString("requestDate").equals(requestDate))
                        .collect(Collectors.toList());
                if (requestList.size() >0) {
                    throw  new JobExecutionException(requestDate +  "exists");
                }

            }
        }

        jobLauncher.run(job , jobParameters);


    }
}
