package com.example.springbatch;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class DefaultLineMapper<T> implements LineMapper<T> {


    private LineTokenizer lineTokenizer;
    private FieldSetMapper fieldSetMapper;


    @Override
    public T mapLine(String line, int lineNumber) throws Exception {
        return null;
    }

    public  void  setLineTokenizer(LineTokenizer lineTokenizer) {
        this.lineTokenizer = lineTokenizer;
    }

    public void  setFieldSetMapper(FieldSetMapper fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }
}
