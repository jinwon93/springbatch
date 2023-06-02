package com.example.springbatch;

import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.Data;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AsyncConfiguration.class , TestBatchConfig.class})
@SpringBatchTest
public class AsyncConfigurationTest {



    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void job() throws  Exception {

        //given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name" , "user1")
                .addLong("date" , new Date().getTime())
                .toJobParameters();

        //when

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        JobExecution jobExecution1 = jobLauncherTestUtils.launchStep("stpe1");

        //then
        Assert.assertEquals(jobExecution.getStatus() , BatchStatus.COMPLETED);
        Assert.assertEquals(jobExecution.getExitStatus() , ExitStatus.COMPLETED);
        StepExecution stepExcution =(StepExecution) ((List) jobExecution1.getStepExecutions()).get(0);


        Assert.assertEquals(stepExcution.getCommitCount() , 11);
        Assert.assertEquals(stepExcution.getReadCount() , 1000);
        Assert.assertEquals(stepExcution.getWriteCount() , 1000);
    }


    @After
    public void clear() {
        jdbcTemplate.execute("delete from customer2");
    }
}