package com.project.member.model.dto;

import com.project.member.persistence.entity.Store;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

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


    public static StoreDto from (Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .reviews(store.getReviews()
                              .stream()
                              .map(ReviewDto::from)
                              .collect(Collectors.toList()))
                .number(store.getNumber())
                .lat(store.getLat())
                .lnt(store.getLnt())
                .rating(store.getRating())
                .build();
    }
}
