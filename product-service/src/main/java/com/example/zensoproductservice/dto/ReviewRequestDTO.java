package com.example.zensoproductservice.dto;

import lombok.Data;

@Data
public class ReviewRequestDTO {
    private String userId;
    private String comment;
    private Double rating;
}
