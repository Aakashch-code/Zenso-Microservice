package com.example.zensoproductservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;

    @Indexed
    private String productName;
    private String companyName;
    private String description;

    private BigDecimal price;

    private Integer quantity;

    @Transient
    private Double AverageRating;

    private Map<String, Object> attributes;

    // --- THIS IS THE FIX ---
    @ReadOnlyProperty
    @DocumentReference(lookup = "{ 'productId' : ?#{#self.id} }")
    private List<Review> reviews;
    // -----------------------

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}