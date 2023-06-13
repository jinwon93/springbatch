package apiService.controller;


import com.example.springbatch.batch.domain.ApiInfo;
import com.example.springbatch.batch.domain.ProductVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiController {



    @PostMapping("/api/product/{id}")
    public String product (@RequestBody ApiInfo info) {

        List<ProductVO> collect = info.getApiRequestVos().stream().map(item -> item.getProductVO()).collect(Collectors.toList());

        return String.valueOf(collect);

    }

}
