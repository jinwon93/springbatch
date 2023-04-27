package com.example.springbatch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;

public class PassCheckingListener implements org.springframework.batch.core.ChunkListener, org.springframework.batch.core.StepExecutionListener {


    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();

        if (!exitCode.equals(ExitStatus.FAILED.getExitCode())){

            return new ExitStatus("PASS");
        }

        return null;
    }

    @Override
    public void beforeChunk(ChunkContext chunkContext) {

    }

    @Override
    public void afterChunk(ChunkContext chunkContext) {

    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {

    }
}
