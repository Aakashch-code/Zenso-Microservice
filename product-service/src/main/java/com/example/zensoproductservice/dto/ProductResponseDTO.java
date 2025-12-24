package com.example.zensoproductservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ProductResponseDTO {
    private String id;
    private String productName;
    private String description;
    private BigDecimal price;
    private Double averageRating;
    private Map<String, Object> attributes;
    private List<ReviewResponseDTO> reviews;
}
