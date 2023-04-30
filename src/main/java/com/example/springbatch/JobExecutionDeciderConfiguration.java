package com.example.springbatch;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
@RequiredArgsConstructor
public class JobExecutionDeciderConfiguration {


    private final JobLauncherApplicationRunner jobLauncherApplicationRunner;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SimpleJobBuilder simpleJobBuilder;



    @Bean
    public Job batchJob(){
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(flow())
                .next(step3())
                .end()
                .build();
    }

    private Flow flow() {
        FlowBuilder<Flow> flowFlowBuilder = new FlowBuilder<>("flow");

        flowFlowBuilder.start(step1())
                .next(step2())
                .end();

        return flowFlowBuilder.build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step")
                .tasklet(((contribution, chunkContext) ->  {
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }



    @Bean
    public Step step2(){
        return stepBuilderFactory.get("evenstep")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))

                .build();
    }


    @Bean
    public Step step3(){
        return stepBuilderFactory.get("evenstep")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))

                .build();
    }
}
