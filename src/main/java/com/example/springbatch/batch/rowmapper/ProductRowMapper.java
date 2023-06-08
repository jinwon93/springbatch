package com.example.springbatch.batch.rowmapper;

import com.example.springbatch.batch.domain.ProductVO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {


    @Override
    public ProductVO mapRow(ResultSet rs, int i) throws SQLException {
        return ProductVO.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getInt("price"))
                .type(rs.getString("type"))
                .build();
    }
}
