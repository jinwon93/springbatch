package com.example.springbatch;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

@Data
@Entity
public class Customer {



    @Id
    private Long id;
    private String name;
    private int age;
    private String year;


    private String firstName;
    private String lastName;
    private String birthdate;

}
