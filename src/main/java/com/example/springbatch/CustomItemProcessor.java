package com.example.springbatch;


import org.springframework.batch.item.ItemProcessor;

import java.util.Locale;

public class CustomItemProcessor implements ItemProcessor<String , String> {



    int cnt = 0;

    @Override
    public String process(String item) throws Exception {

        cnt++;

        return (item + cnt).toUpperCase();
    }
}
