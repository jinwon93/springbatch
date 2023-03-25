package com.example.springbatch.job;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobExecutionConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


     @Bean
     public  Job job(){
         return jobBuilderFactory.get("job")
                 .start(step1())
                 .next(step2())
                 .build();
     }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("hellowStep1")
                .tasklet(
                    new CustomTasklet())
                .build();
    }


    @Bean
    public Step step2(){
         // jobInstance B ==> JobExecution status가 failed면 JobExecution에 저장
        return stepBuilderFactory.get("hellowStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

                        throw  new RuntimeException("step2 hjas faild");
//                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
