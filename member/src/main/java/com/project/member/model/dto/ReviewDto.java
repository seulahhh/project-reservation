package com.project.member.model.dto;

import com.project.member.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewDto {
    private String title;
    private String body;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static ReviewDto from (Review review) {
        return ReviewDto.builder()
                .title(review.getTitle())
                .body(review.getBody())
                .createAt(review.getCreateAt())
                .modifiedAt(review.getModifiedAt())
                .build();
    }
}
