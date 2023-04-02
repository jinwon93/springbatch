package com.example.springbatch.job;


import com.example.springbatch.entitiy.Member;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class JobLanucherController {

    @Autowired
    private Job job;



    @Autowired
    private JobLauncher jobLauncher;




    @PostMapping("batch")
    public String launch(@RequestBody Member member) throws JobInstanceAlreadyExistsException, JobExecutionAlreadyRunningException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("id" , member.getId())
                .addDate("data" , new Date())
                .toJobParameters();

        jobLauncher.run(job , jobParameters);

        return "batch run ";
    }
}
