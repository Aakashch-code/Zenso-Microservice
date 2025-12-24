package com.example.zensoproductservice.service;

import com.example.zensoproductservice.dto.ReviewRequestDTO;
import com.example.zensoproductservice.dto.ReviewResponseDTO;
import com.example.zensoproductservice.mapper.ReviewMapper;
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

    @Autowired
    private ReviewMapper reviewMapper;

    public List<ReviewResponseDTO> getAllReviews() {
        return reviewRepo.findAll()
                .stream()
                .map(reviewMapper::toResponseDTO)
                .toList();
    }

    public List<ReviewResponseDTO> getReviewsByProductId(String productId) {
        return reviewRepo.findByProductId(productId)
                .stream()
                .map(reviewMapper::toResponseDTO)
                .toList();
    }

    public Review createReview(ReviewRequestDTO dto, String productId) {
        if (!productRepo.existsById(productId)) {
            throw new RuntimeException("Cannot review non-existent product");
        }

        Review review = reviewMapper.toEntity(dto);
        review.setProductId(productId);
        return reviewRepo.save(review);
    }

    public Review updateReview(ReviewRequestDTO dto, String id) {
        Review review = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (dto.getComment() != null) review.setComment(dto.getComment());
        if (dto.getRating() != null) review.setRating(dto.getRating());

        return reviewRepo.save(review);
    }

    public void deleteReviewById(String id) {
        reviewRepo.deleteById(id);
    }
}
