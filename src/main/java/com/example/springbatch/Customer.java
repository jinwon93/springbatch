package com.example.springbatch;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

@Data
@AllArgsConstructor
public class Customer {



    @Id
    private Long id;
    private String name;
    private int age;




}
