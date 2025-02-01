package com.project.global.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    private Long id;
    private String name;
    private List<ReviewDto> reviews;
    private String number;
    private Double rating;
    private Double lat;
    private Double lnt;
    private Double distance;
}
