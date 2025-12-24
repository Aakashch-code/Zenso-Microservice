package com.example.zensoproductservice.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ReviewResponseDTO {
    private String id;
    private String userId;
    private String comment;
    private Double rating;
    private Instant createdAt;
}
