package com.example.zensoproductservice.repository;

import com.example.zensoproductservice.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    // Fetch reviews for a SINGLE product
    List<Review> findByProductId(String productId);

    // Fetch reviews for a LIST of product IDs (Optimized Bulk Fetch)
    List<Review> findByProductIdIn(List<String> productIds);

    // Delete all reviews belonging to a product (Cascading Delete)
    void deleteByProductId(String productId);
}