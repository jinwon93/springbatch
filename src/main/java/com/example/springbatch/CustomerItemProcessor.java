package com.example.springbatch;

import org.springframework.batch.item.ItemProcessor;

import java.util.Locale;


public class CustomerItemProcessor implements ItemProcessor<Customer , Customer> {


    @Override
    public Customer process(Customer item) throws Exception {

        item.setCustomerName(item.getCustomerName().toUpperCase(Locale.ROOT));

        return item;
    }
}
