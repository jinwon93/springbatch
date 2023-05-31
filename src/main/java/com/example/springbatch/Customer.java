package com.example.springbatch;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import java.util.Date;

@Data
@AllArgsConstructor
public class Customer {



    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private  Date birthdate;





}
