package com.example.springbatch;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
@RequiredArgsConstructor
public class SimpleFlowConfiguration {


    private final JobLauncherApplicationRunner jobLauncherApplicationRunner;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SimpleJobBuilder simpleJobBuilder;



    @Bean
    public Job batchJob(){
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .start(flow1())
                    .on("COMPLETED")
                    .to(flow2())
                .from(flow1())
                    .on("FAILED")
                    .to(flow3())
                .end()
                .build();
    }

    private Flow flow1() {
        FlowBuilder<Flow> flowFlowBuilder = new FlowBuilder<>("flow");

        flowFlowBuilder.start(step1())
                .next(step2())
                .end();

        return flowFlowBuilder.build();
    }

    private Flow flow2() {
        FlowBuilder<Flow> flowFlowBuilder = new FlowBuilder<>("flow");

        flowFlowBuilder.start(flow3())
                .next(step5())
                .next(step6())
                .end();

        return flowFlowBuilder.build();
    }


    private Flow flow3() {
        FlowBuilder<Flow> flowFlowBuilder = new FlowBuilder<>("flow");

        flowFlowBuilder.start(step1())
                .next(step3())
                .next(step4())
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
        return stepBuilderFactory.get("eventstep")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))

                .build();
    }


    @Bean
    public Step step3(){
        return stepBuilderFactory.get("eventstep")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))

                .build();
    }


    @Bean
    public Step step4(){
        return stepBuilderFactory.get("eventstep")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))

                .build();
    }

    @Bean
    public Step step5(){
        return stepBuilderFactory.get("eventstep")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))

                .build();
    }

    @Bean
    public Step step6(){
        return stepBuilderFactory.get("eventstep")
                .tasklet(((stepContribution, chunkContext) ->  {
                    return RepeatStatus.FINISHED;
                }))

                .build();
    }



}
