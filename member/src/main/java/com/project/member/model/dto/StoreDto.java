package com.project.member.model.dto;

import com.project.member.domain.entity.Review;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreDto {
    private String name;
    private List<Review> reviews;
    private String number;
    private Double lat;
    private Double lnt;
}
