package com.project.global.dto;

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
}
