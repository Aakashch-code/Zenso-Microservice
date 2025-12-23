package com.example.zensoproductservice.controller;

import com.example.zensoproductservice.model.Review;
import com.example.zensoproductservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Changed base path to support nested URLs better
public class ReviewController {

    @Autowired
    private ReviewService service;

    // Get all reviews (Admin utility)
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> fetchAllReviews() {
        return ResponseEntity.ok(service.getAllReviews());
    }

    // Create a review for a specific product
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> createReview(@PathVariable String productId, @RequestBody Review review) {
        Review savedReview = service.createReview(review, productId);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }
}