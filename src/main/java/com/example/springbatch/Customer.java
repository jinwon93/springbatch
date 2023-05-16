package com.example.springbatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Data
@AllArgsConstructor
public class Customer {


    private String customerName;
}
