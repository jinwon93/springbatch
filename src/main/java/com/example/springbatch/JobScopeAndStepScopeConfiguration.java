package com.example.springbatch;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
@RequiredArgsConstructor
public class JobScopeAndStepScopeConfiguration {


    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;



    public Job job() {
        return jobBuilderFactory.get("batchJob")
                .start(step1(null))
                .next(step2(null))
                .listener(new JobListener())
                .build();
    }

    @Bean
    @JobScope
    public Step step1(@Value("#{jobParameters['message']}") String message){
        return stepBuilderFactory.get("step1")
                .tasklet(tasklet1(null))
                .build();
    }



    @Bean
    @JobScope
    public Step step2(@Value("#{jobParameters['message']}") String message){
        return stepBuilderFactory.get("step1")
                .tasklet(tasklet2(null))
                .build();
    }



    @Bean
    @StepScope
    public Tasklet tasklet1(@Value("#{jobExecutionContext['name']}") String name ){
        return (stepContribution ,chunkContext)-> {
            return RepeatStatus.FINISHED;
        };
    }


    @Bean
    @StepScope
    public Tasklet tasklet2(@Value("#{stepExecutionContext['name']}") String name ){
        return (stepContribution ,chunkContext)-> {
            return RepeatStatus.FINISHED;
        };
    }




}
