package com.example.springbatch;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
@RequiredArgsConstructor
public class JobConfiguration {


    private final JobLauncherApplicationRunner jobLauncherApplicationRunner;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(step1())
                .next(step2())
                .build();
    }


    @Bean
    public Job jobFlow(){
        return this.jobBuilderFactory.get("job")
                .start(flow())
                .next(step5())
                .end()
                .build();

    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("hellowStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        return RepeatStatus.FINISHED;
                    }
                })
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

    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");

        flowBuilder.start(step3())
                .next(step4())
                .end();

        return flowBuilder.build();

    }

    @Bean
    public Step step3(){
        // jobInstance B ==> JobExecution status가 failed면 JobExecution에 저장
        return stepBuilderFactory.get("hellowStep3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

                        throw  new RuntimeException("step2 hjas faild");
//                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }


    @Bean
    public Step step4(){
        // jobInstance B ==> JobExecution status가 failed면 JobExecution에 저장
        return stepBuilderFactory.get("hellowStep4")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

                        throw  new RuntimeException("step2 hjas faild");
//                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step step5(){
        // jobInstance B ==> JobExecution status가 failed면 JobExecution에 저장
        return stepBuilderFactory.get("hellowStep4")
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
