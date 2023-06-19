package com.example.springbatch.batch.chunk;

import com.example.springbatch.batch.domain.ApiRequestVo;
import com.example.springbatch.batch.domain.ApiResponseVo;
import com.example.springbatch.service.ApiAbstractService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

public class ItemWriter extends FlatFileItemWriter<ApiRequestVo> {


    private final ApiAbstractService abstractService;


    public ItemWriter(ApiAbstractService abstractService) {
        this.abstractService = abstractService;

    }



    @Override
    public void write(List<? extends ApiRequestVo> items) throws Exception {

        ApiResponseVo responseVo = abstractService.apiResponseVo(items);


        items.forEach(item -> item.setApiResponseVo(responseVo) );
        super.setResource(new FileSystemResource("\\src\\main\\resources\\product.txt"));
        super.open(new ExecutionContext());
        super.setLineAggregator(new DelimitedLineAggregator<>());
        super.setAppendAllowed(true);
        super.write((Chunk<? extends ApiRequestVo>) items);
    }
}
