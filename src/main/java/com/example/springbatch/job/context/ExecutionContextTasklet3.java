package com.example.springbatch.job.context;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionContextTasklet3 implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        Object name = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("step4Name");
        if (name == null) {
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("step4Name" , "user1");
            throw  new RuntimeException("step2 run faild");
        }
        return RepeatStatus.FINISHED;
    }
}
