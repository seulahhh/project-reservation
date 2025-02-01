package com.project.reservation.model.dto.form;

import com.project.reservation.persistence.entity.Review;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private Long customerId;
    private Long storeId;
    private String title;
    private String body;
    private Double rating;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .customerId(review.getCustomerId())
                .id(review.getId())
                .storeId(review.getStore().getId())
                .title(review.getTitle())
                .body(review.getBody())
                .rating(review.getRating())
                .build();
    }
}
