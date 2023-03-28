package com.example.springbatch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExecutionContextTasklet1 implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        ExecutionContext executionContext = stepContribution.getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext = stepContribution.getStepExecution().getExecutionContext();

        String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

        if (executionContext.get("jobName") ==null) {
            executionContext.put("jobName" , jobName);
        }

        if (stepExecutionContext.get("stepName") == null) {
            stepExecutionContext.put("stepName" , stepName);
        }

        log.info("jobName" + executionContext.get("jobName"));
        log.info("stepName" + stepExecutionContext.get("stepName"));
        return RepeatStatus.FINISHED;
    }
}
