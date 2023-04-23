package com.example.springbatch;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
@RequiredArgsConstructor
public class BatchStatusExitStatusConfiguration {


    private final JobLauncherApplicationRunner jobLauncherApplicationRunner;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SimpleJobBuilder simpleJobBuilder;



    @Bean
    public Job batchJob(){
        return jobBuilderFactory.get("job")
                .start(step1())
                .on("FAILED")
                .to(step2())
                .end()
                .build();
    }


    @Bean
    public Step step1(){
        return stepBuilderFactory.get("hellowStep1")
                .tasklet(((stepContribution, chunkContext) ->  {
                    stepContribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }



    @Bean
    public Step step2(){
        return stepBuilderFactory.get("hellowStep2")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }


    @Bean
    public Step step3(){
        return stepBuilderFactory.get("hellowStep3")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
