package com.example.springbatch;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class StartNextConfiguration {


    private final JobLauncherApplicationRunner jobLauncherApplicationRunner;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SimpleJobBuilder simpleJobBuilder;



    @Bean
    public Job batchJob(){
        return jobBuilderFactory.get("job")
                .start(flowA())
                .next(step3())
                .next(flowB())
                .next(step6())
                .end()
                .build();
    }

    @Bean
    public Flow flowA() {
        FlowBuilder<Flow> flowFlowBuilder  = new FlowBuilder<>("flowA");
        flowFlowBuilder.start(step1())
                .next(step2())
                .end();
        return flowFlowBuilder.build();
    }

    @Bean
    public Flow flowB() {
        FlowBuilder<Flow> flowFlowBuilder  = new FlowBuilder<>("flow");
        flowFlowBuilder.start(step4())
                .next(step5())
                .end();
        return flowFlowBuilder.build();
    }
    @Bean
    public Step step1(){
        return stepBuilderFactory.get("hellowStep1")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }


    @Bean
    public Step step2(){
        return stepBuilderFactory.get("hellowStep1")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step step3(){
        return stepBuilderFactory.get("hellowStep1")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }


    @Bean
    public Step step4(){
        return stepBuilderFactory.get("hellowStep1")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step step5(){
        return stepBuilderFactory.get("hellowStep1")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
    @Bean
    public Step step6(){
        return stepBuilderFactory.get("hellowStep1")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

}
