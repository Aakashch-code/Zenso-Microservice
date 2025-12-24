package com.example.zensoproductservice.mapper;

import com.example.zensoproductservice.dto.ProductRequestDTO;
import com.example.zensoproductservice.dto.ProductResponseDTO;
import com.example.zensoproductservice.model.Product;
import com.example.zensoproductservice.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReviewMapper.class)
public interface ProductMapper {

    // Product + reviews + rating → DTO
    @Mapping(target = "reviews", source = "reviews")
    @Mapping(target = "averageRating", source = "averageRating")
    ProductResponseDTO toResponseDTO(
            Product product,
            List<Review> reviews,
            Double averageRating
    );

    // DTO → Entity
    Product toEntity(ProductRequestDTO dto);
}
