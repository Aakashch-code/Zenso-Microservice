package com.example.zensoproductservice.controller;

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

    // 1. Get all reviews (Admin)
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> fetchAllReviews() {
        return ResponseEntity.ok(service.getAllReviews());
    }

    // 2. Create a review for a specific product
    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> createReview(@PathVariable String productId, @RequestBody Review review) {
        Review savedReview = service.createReview(review, productId);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    // 3. NEW: Get all reviews for a specific Product
    // Usage: GET /api/products/12345/reviews
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<Review>> fetchReviewsByProductId(@PathVariable String productId) {
        return ResponseEntity.ok(service.getReviewsByProductId(productId));
    }

    // 4. Get specific review
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> fetchReviewById(@PathVariable String reviewId) {
        return ResponseEntity.ok(service.getReviewById(reviewId));
    }

    // 5. Update review
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable String reviewId, @RequestBody Review review) {
        return ResponseEntity.ok(service.updateReview(review, reviewId));
    }

    // 6. Delete specific review
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReviewById(@PathVariable String id) {
        service.deleteReviewById(id);
        return ResponseEntity.noContent().build(); // Returns HTTP 204 (Standard for Delete)
    }

    // REMOVED: deleteAllReviews
    // It is very dangerous to have a "Delete All" endpoint exposed in a Controller
    // without strict security. It's better to remove it for now.
}