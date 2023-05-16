package com.example.springbatch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class CustomerItemWriter  implements ItemWriter<Customer> {
    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        chunk.forEach(item -> item.setCustomerName(String.valueOf(item)));
    }
}
