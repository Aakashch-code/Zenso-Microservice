package com.example.zensoproductservice.service;

import com.example.zensoproductservice.model.Review;
import com.example.zensoproductservice.repository.ProductRepository;
import com.example.zensoproductservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private ProductRepository productRepo;

    // 1. Get all reviews (Admin use only usually)
    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    // 2. NEW: Get reviews specifically for one product
    public List<Review> getReviewsByProductId(String productId) {
        return reviewRepo.findByProductId(productId); // You need to ensure this method exists in your Repository
    }

    // 3. Create Review
    public Review createReview(Review review, String productId) {
        // Validation: Check if product exists
        if (!productRepo.existsById(productId)) {
            throw new RuntimeException("Cannot review non-existent product: " + productId);
        }

        review.setProductId(productId);
        return reviewRepo.save(review);
    }

    public Review getReviewById(String id) {
        return reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    public Review updateReview(Review review, String id) {
        Review existingReview = getReviewById(id);

        if (review.getComment() != null) {
            existingReview.setComment(review.getComment());
        }

        // Use strict check for rating (0 can be a valid rating in some systems, check your logic)
        if (review.getRating() != null) {
            existingReview.setRating(review.getRating());
        }

        return reviewRepo.save(existingReview);
    }

    public void deleteReviewById(String id) {
        if (!reviewRepo.existsById(id)) {
            throw new RuntimeException("Cannot delete. Review not found: " + id);
        }
        reviewRepo.deleteById(id);
    }
}