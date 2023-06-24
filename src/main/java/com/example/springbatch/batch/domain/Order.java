package com.example.springbatch.batch.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Order {

    @Id
    private  Long oderId;
    private  String  productName;
    private LocalDateTime localDateTime;
}
