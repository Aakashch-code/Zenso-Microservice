package com.example.zensoproductservice.controller;

import com.example.zensoproductservice.dto.ReviewRequestDTO;
import com.example.zensoproductservice.dto.ReviewResponseDTO;
import com.example.zensoproductservice.model.Review;
import com.example.zensoproductservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> fetchAllReviews() {
        return ResponseEntity.ok(service.getAllReviews());
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> createReview(
            @PathVariable String productId,
            @RequestBody ReviewRequestDTO dto
    ) {
        return new ResponseEntity<>(service.createReview(dto, productId), HttpStatus.CREATED);
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> fetchReviewsByProductId(
            @PathVariable String productId
    ) {
        return ResponseEntity.ok(service.getReviewsByProductId(productId));
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable String reviewId,
            @RequestBody ReviewRequestDTO dto
    ) {
        return ResponseEntity.ok(service.updateReview(dto, reviewId));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReviewById(@PathVariable String id) {
        service.deleteReviewById(id);
        return ResponseEntity.noContent().build();
    }
}
