package com.example.springbatch;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;


@Configuration
@RequiredArgsConstructor
// ItmeReader ItemWriter ItemProcessor
public class FlatFilesConfiguration {


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

                .writer(new ItemStreamWriter<Customer>() {
                            @Override
                            public void write(Chunk<? extends Customer> chunk) throws Exception {
                                return;
                            }
                        }
                )
                .build();
    }

    public ItemReader itemReader(){

        return  new FlatFileItemReaderBuilder<Customer>()
                .name("flatFile")
                .resource(new ClassPathResource("/customer.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Customer.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("name" ,"age" , "year")
                .build();
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
