package com.example.zensoproductservice.mapper;

import com.example.zensoproductservice.dto.ReviewRequestDTO;
import com.example.zensoproductservice.dto.ReviewResponseDTO;
import com.example.zensoproductservice.model.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewResponseDTO toResponseDTO(Review review);

    Review toEntity(ReviewRequestDTO dto);
}
