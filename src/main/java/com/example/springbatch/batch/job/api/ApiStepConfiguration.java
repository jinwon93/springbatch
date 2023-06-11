package com.example.springbatch.batch.job.api;



import com.example.springbatch.batch.domain.ApiRequestVo;
import com.example.springbatch.batch.domain.ProductVO;
import com.example.springbatch.batch.partition.ProductPartitioner;
import com.example.springbatch.service.ApiService;
import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.Step;

import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.messaging.support.IdTimestampMessageHeaderInitializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@RequiredArgsConstructor
public class ApiStepConfiguration {



    private final StepBuilderFactory stepBuilderFactory;

    private final DataSource dataSource;
    private final ApiService apiService;

    private int chunkSize = 10;


    @Bean
    public Step apiMasterStep() throws Exception {
        return stepBuilderFactory.get("apiMasterStep")
                .partitioner(apiSlaveStep().getName() , partitioner())
                .step(apiSlaveStep())
                .gridSize(2)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor taskExecutor  =new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setThreadNamePrefix("api-thread");
        return  taskExecutor;
    }


    @Bean
    public Step apiSlaveStep() throws Exception {

        return stepBuilderFactory.get("apiSlaveStep")
                .<ProductVO , ProductVO>chunk(chunkSize)
                .reader(itemReader(null))
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemWriter itemWriter() {

        ClassifierCompositeItemWriter<ApiRequestVo> writer
                = new ClassifierCompositeItemWriter<>();

        Classifier<ApiRequestVo , ItemWriter<? super   ApiRequestVo>> classifier = apiRequestVo -> null;

        Map<String , ItemWriter<ApiRequestVo>> writerMap = new HashMap<>();

        for (int i = 1; i <= 3; i++) {
            writerMap.put(String.valueOf(i), chunk -> {
                return;
            });
        }

        writer.setClassifier(classifier);
        return writer;
    }

    @Bean
    public ItemProcessor itemProcessor() {

        ClassifierCompositeItemProcessor<ProductVO , ApiRequestVo> processor
                = new ClassifierCompositeItemProcessor<ProductVO, ApiRequestVo>();

        Classifier<ProductVO , ItemProcessor<? , ? extends  ApiRequestVo>> classifier = productVO -> null;

        Map<String , ItemProcessor<ProductVO , ApiRequestVo>> processorMap = new HashMap<>();



        for (int i= 1; i <=3; i++) {

            processorMap.put(String.valueOf(i), item -> ApiRequestVo.builder()
                    .id(item.getId())
                    .productVO(item)
                    .build());
        }

        processor.setClassifier(classifier);
        return processor;
    }

    @Bean
    public ProductPartitioner partitioner(){
        ProductPartitioner productPartitioner = new ProductPartitioner();
        productPartitioner.setDataSource(dataSource);
        return productPartitioner;
    }

    @Bean
    @StepScope
    public ItemReader<ProductVO> itemReader(@Value("#{stepExecutionContext['product']}") ProductVO productVO) throws Exception {

        JdbcPagingItemReader<ProductVO> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(dataSource);
        reader.setPageSize(chunkSize);
        reader.setRowMapper(new BeanPropertyRowMapper(ProductVO.class));

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, name, price, type");
        queryProvider.setFromClause("from product");
        queryProvider.setWhereClause("where type = :type");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.DESCENDING);
        queryProvider.setSortKeys(sortKeys);

        reader.setParameterValues(QueryGenerator.getParameterForQuery("type", productVO.getType()));
        reader.setQueryProvider(queryProvider);
        reader.afterPropertiesSet();

        return reader;
    }
}
