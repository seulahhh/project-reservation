//package com.project.reservation.persistence.entity;
//
//import com.project.member.persistence.entity.Store;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//
//import java.time.LocalDateTime;
//
///**
// * 추후 디테일 잡을 때 진행
// */
//@Entity
//@Getter
//@AllArgsConstructor
//@RequiredArgsConstructor
//@Builder
//public class StoreAvailability {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "store_id")
//    private Store store;
//
//    private LocalDateTime startDate;
//    private LocalDateTime endDate;
//
//    private Integer availablePerDay;
//}
