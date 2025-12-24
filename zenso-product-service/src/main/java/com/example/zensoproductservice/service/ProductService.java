package com.example.zensoproductservice.service;

import com.example.zensoproductservice.dto.ProductRequestDTO;
import com.example.zensoproductservice.dto.ProductResponseDTO;
import com.example.zensoproductservice.mapper.ProductMapper;
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

    @Autowired
    private ProductMapper productMapper;

    // 1. FETCH ALL
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepo.findAll();
        if (products.isEmpty()) return List.of();

        List<String> productIds = products.stream().map(Product::getId).toList();
        List<Review> allReviews = reviewRepo.findByProductIdIn(productIds);

        Map<String, List<Review>> reviewsMap =
                allReviews.stream().collect(Collectors.groupingBy(Review::getProductId));

        return products.stream().map(product -> {
            List<Review> reviews = reviewsMap.getOrDefault(product.getId(), List.of());
            double avg = calculateAverage(reviews);

            return productMapper.toResponseDTO(product, reviews, avg);
        }).toList();
    }

    // 2. GET SINGLE
    public ProductResponseDTO getProductById(String id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Review> reviews = reviewRepo.findByProductId(id);
        return productMapper.toResponseDTO(product, reviews, calculateAverage(reviews));
    }

    // 3. CREATE
    public Product postProduct(ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        product.setAverageRating(0.0);
        return productRepo.save(product);
    }

    // 4. UPDATE
    public Product updateProduct(String id, ProductRequestDTO dto) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (dto.getProductName() != null) product.setProductName(dto.getProductName());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getAttributes() != null) product.setAttributes(dto.getAttributes());

        return productRepo.save(product);
    }

    // 5. DELETE
    public void deleteProduct(String id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        reviewRepo.deleteByProductId(id);
        productRepo.deleteById(id);
    }

    private double calculateAverage(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }
}
