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
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class FlatFilesDelimitedConfiguration {



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

        List<Customer> customers = Arrays.asList(new Customer(1L,  "hong gil dong" , 21)),
                new Customer(2L,  "hong gil dong" , 21  ),
                new Customer(3 ,  "hong gil dong" , 22  );

        ListItemReader<Customer> reader = new ListItemReader<>(customers);

        return  reader;

    }

    public ItemWriter<? super Object> customerItemWriter() {

        return  new FlatFileItemWriterBuilder<>()
                .name("flatFileWriter")
                .resource(new FileSystemResource("C:\\Users\\USER\\Downloads\\spring-batch\\springbatch\\src\\main\\resources"))
                .delimited()
                .delimiter("|")
                .names( new String[]{"id", "name" ,"age"})
                .build();
    }

}
