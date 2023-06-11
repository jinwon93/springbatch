package com.example.springbatch.batch.domain;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseVo {

    private String status;
    private String msg;
}
