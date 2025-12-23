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

    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }


    public Review createReview(Review review, String productId) {
        // 1. Safety Check: Does this product actually exist?
        if (!productRepo.existsById(productId)) {
            throw new RuntimeException("Cannot review non-existent product: " + productId);
        }

        // 2. Link and Save
        review.setProductId(productId);
        return reviewRepo.save(review);
    }
}
