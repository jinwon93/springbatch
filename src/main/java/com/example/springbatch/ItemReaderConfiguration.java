package com.example.springbatch;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
@RequiredArgsConstructor
// ItmeReader ItemWriter ItemProcessor
public class ItemReaderConfiguration {


    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;



    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean

    public Step step1(){
        return stepBuilderFactory.get("step1")
                .<Customer , Customer>chunk(5)
                .reader(itemReader())
                .processor(itemProcessor()
                )
                .writer(itemWriter()
                )
                .build();
    }

    @Bean
    public ItemWriter<? super Customer> itemWriter() {
        return new CustomerItemWriter();
    }

    @Bean
    public ItemReader<? extends Customer> itemReader() {

        return  new CustomerItemReader(Arrays.asList(new Customer("user")));
    }

    @Bean
    public CustomerItemProcessor itemProcessor() {
         return new CustomerItemProcessor();
    }


    @Bean

    public Step step2(){
        return stepBuilderFactory.get("step1")
                .tasklet(((contribution, chunkContext) -> {
                    return  RepeatStatus.FINISHED;
                }))
                .build();
    }








}
