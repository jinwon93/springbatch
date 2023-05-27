package com.example.springbatch;


import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class JpaCusorConfiguration {



    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory em;

    private int chunkSize = 10;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .chunk(chunkSize)
                .reader(customerItemReader())
                .writer(customerItemWriter())
                .build();
    }

    private ItemReader<?  extends  Customer> customerItemReader() {

        Map<String , Object>  parameters = new HashMap<>();
        parameters.put("firstname" , "A%");

        return new JpaCursorItemReaderBuilder<Customer>()
                .name("jpaCursorItemReader")
                .entityManagerFactory(em)
                .queryString("select c from Cusotmer c where firstname like :firstname")
                .parameterValues(parameters)
                .build();

    }

    public ItemWriter<? super Object> customerItemWriter() {

        return  items -> {
            for (Object item : items) {
                log.info((String) item);
            }
        };
    }

}
