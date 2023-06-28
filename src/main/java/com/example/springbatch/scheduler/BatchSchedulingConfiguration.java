package com.example.springbatch.scheduler;



import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BatchSchedulingConfiguration {


    @Autowired
    private JobLauncher jobLauncher;


    @Autowired
    private Job orderProcessingJob;


    @Scheduled(cron = "0 0 0 * * 1") // 매주 월요일 자정에 실행
    public void  runOrderProcessingJob() throws JobExecutionException {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID" , String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        jobLauncher.run(orderProcessingJob , jobParameters);


    }
}
