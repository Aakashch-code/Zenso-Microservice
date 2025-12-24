package com.example.zensoproductservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductRequestDTO {
    private String productName;
    private String description;
    private BigDecimal price;
    private Map<String, Object> attributes;
}
