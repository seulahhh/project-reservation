package com.project.member.model.dto;

import com.project.member.persistence.entity.Review;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class ReviewDto {
    private Long id;

    private String title;
    private String body;
    private Double rating;

    private Long storeId;
    private Long customerId;

    public static ReviewDto from (Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .body(review.getBody())
                .rating(review.getRating())
                .storeId(review.getStore().getId())
                .customerId(review.getCustomerId())
                .build();
    }
}
