package com.example.zensoproductservice.service;

import com.example.zensoproductservice.model.Product;
import com.example.zensoproductservice.model.Review;
import com.example.zensoproductservice.repository.ProductRepository;
import com.example.zensoproductservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    // --- 1. OPTIMIZED FETCH ALL (Solves N+1 Problem) ---
    public List<Product> getAllProducts() {
        List<Product> products = productRepo.findAll();
        if (products.isEmpty()) return products;

        // Step A: Collect all Product IDs
        List<String> productIds = products.stream().map(Product::getId).toList();

        // Step B: Fetch ALL related reviews in ONE database query
        List<Review> allReviews = reviewRepo.findByProductIdIn(productIds);

        // Step C: Group reviews by Product ID in memory
        Map<String, List<Review>> reviewsMap = allReviews.stream()
                .collect(Collectors.groupingBy(Review::getProductId));

        // Step D: Attach reviews and calculate rating
        for (Product product : products) {
            List<Review> productReviews = reviewsMap.getOrDefault(product.getId(), List.of());
            product.setReviews(productReviews);
            product.setAverageRating(calculateAverage(productReviews));
        }

        return products;
    }

    // --- 2. GET SINGLE PRODUCT ---
    public Product getProductById(String id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        // Fetch reviews specifically for this product
        List<Review> reviews = reviewRepo.findByProductId(id);
        product.setReviews(reviews);
        product.setAverageRating(calculateAverage(reviews));

        return product;
    }

    // --- 3. CREATE ---
    public Product postProduct(Product product) {
        // Initialize with 0 rating
        product.setAverageRating(0.0);
        return productRepo.save(product);
    }

    // --- 4. UPDATE ---
    public Product updateProduct(String id, Product updatedProduct) {
        Product existingProduct = getProductById(id); // Re-use logic to ensure it exists

        // Update fields (In a real app, use a Mapper)
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setAttributes(updatedProduct.getAttributes());
        // Don't overwrite ID or Reviews here usually

        return productRepo.save(existingProduct);
    }

    // --- 5. DELETE (Cascading) ---
    public void deleteProduct(String id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found with ID: " + id);
        }
        // SAFETY: Delete all reviews for this product first so we don't have orphan data
        reviewRepo.deleteByProductId(id);
        productRepo.deleteById(id);
    }

    // Helper Method
    private double calculateAverage(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) return 0.0;
        double sum = reviews.stream().mapToDouble(Review::getRating).sum();
        return sum / reviews.size();
    }
}