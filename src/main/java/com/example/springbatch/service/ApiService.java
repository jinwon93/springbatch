package com.example.springbatch.service;

import com.example.springbatch.batch.domain.ApiInfo;
import com.example.springbatch.batch.domain.ApiResponseVo;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ApiService extends  ApiAbstractService {



    @Override
    protected ApiResponseVo doApiService(RestTemplate restTemplate, ApiInfo apiInfo) {

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://localhost:8081/api/product/1", apiInfo, String.class);
        HttpStatusCode statusCode = stringResponseEntity.getStatusCode();
        ApiResponseVo build = ApiResponseVo.builder().status(String.valueOf(statusCode)).msg(stringResponseEntity.getBody()).build();
        return build;
    }
}
