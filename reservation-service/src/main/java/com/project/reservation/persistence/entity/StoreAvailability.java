package com.project.reservation.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class StoreAvailability {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private LocalDate date;

    @ElementCollection
    private List<LocalTime> availableTimes;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer availablePerTime;
}

