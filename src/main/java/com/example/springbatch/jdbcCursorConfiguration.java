package com.example.springbatch;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class jdbcCursorConfiguration {



    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

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

    private ItemReader<String> customerItemReader() {

        return new JdbcCursorItemReaderBuilder<>()
                .name("jdbcCursorItemReader")
                .fetchSize(chunkSize)
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
