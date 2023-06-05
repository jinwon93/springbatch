package com.example.springbatch.batch.domain;


import lombok.Data;

@Data
public class ProductVO {

    private Long id;

    private String name;
    private String price;
    private String type;
}
