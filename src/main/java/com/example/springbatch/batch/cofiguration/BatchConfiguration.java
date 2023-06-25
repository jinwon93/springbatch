package com.example.springbatch.batch.cofiguration;


import com.example.springbatch.batch.domain.Order;
import org.aspectj.weaver.ast.Or;
import org.hibernate.sql.results.graph.DatabaseSnapshotContributor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
class BatchConfiguration {


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;


    @Bean
    public ItemReader<Order> orderReader() {
        JdbcCursorItemReader<Order> reader = new JdbcCursorItemReader<>();

        reader.setDataSource(dataSource);
        reader.setSql("SELECT order_id , product_name , order_date FROM orders");
        reader.setRowMapper(new BeanPropertyRowMapper<>(Order.class));
        return  reader;
    }


    @Bean
    public ItemProcessor<Order , Order> orderProcessor() {
        return order -> {
            return order;
        };
    }


    @Bean
    public ItemWriter<Order> orderWriter() {
        return items -> {
            for (Order order : items) {
                // 가공된 주문 데이터를 저장 또는 외부 시스템에 전달하는 로직 수행
                System.out.println("Processed order: " + order.getOderId() + " - " + order.getProductName());
            }
        };
    }


    @Bean
    public Step orderProcessingStep(ItemReader<Order> reader , ItemProcessor<Order, Order> processor, ItemWriter<Order> writer) {

        return stepBuilderFactory.get("orderProcessingStep")
                .<Order , Order>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public Job  orderProcessingJob(Step orderProcessingStep)  {
        return  jobBuilderFactory.get("orderProcessingStep")
                .incrementer(new RunIdIncrementer())
                .flow(orderProcessingStep)
                .end()
                .build();
    }
}
