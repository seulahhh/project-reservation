package com.project.global.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableSlotDto {
    private LocalTime startTime;
    private boolean available;
    private int currentCount;
}
