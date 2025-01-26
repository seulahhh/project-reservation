package com.project.member.model.dto;

import lombok.*;

/**
 * 위치 정보를 담을 Dto
 */
@Getter
@Builder
public class LocationDto {
    private Double lat;
    private Double lnt;

    public static LocationDto of (Double lat, Double lnt) {
        return LocationDto.builder().lat(lat).lnt(lnt).build();
    }
}
