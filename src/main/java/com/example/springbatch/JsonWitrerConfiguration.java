package com.example.springbatch;


import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class JsonWitrerConfiguration {



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
                .writer((ItemWriter<? super Object>) customerItemWriter())
                .processor((ItemProcessor<? super Object, ?>) customerItemProcessor())
                .build();
    }


    @Bean
    public ItemProcessor<? super String,String> customerItemProcessor() {

        List itemProcessor = new ArrayList();
        itemProcessor.add(new CustomItemProcessor());


        return  new CompositeItemProcessorBuilder<>()
                .delegates(itemProcessor)
                .build();
    }

    private ItemReader<?  extends  Customer> customerItemReader() {

        List<Customer> customers = Arrays.asList(new Customer(1L,  "hong gil dong" , 21),
                new Customer(2L,  "hong gil dong" , 22  ),
                new Customer(3L,  "hong gil dong" , 23  ));

        ListItemReader<Customer> reader = new ListItemReader<>(customers);

        return  reader;

    }

    public ItemWriter<? super Customer> customerItemWriter() {

        return  new JsonFileItemWriterBuilder<Customer>()

                .name("jsonFileWriter")
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource("C:\\Users\\USER\\Downloads\\spring-batch\\springbatch\\src\\main\\resources\\customer.json"))
                .build();
    }

}
