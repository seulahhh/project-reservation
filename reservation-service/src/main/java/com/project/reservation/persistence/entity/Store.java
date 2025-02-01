package com.project.reservation.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private Double rating = 0.0;

    @Column(name = "manager_id")
    private Long managerId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store", orphanRemoval =
            true)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    private String number;
    private Double lat;
    private Double lnt;

    public void addReview(Review review) {
        review.setStore(this);
        this.getReviews().add(review);
        log.info("** {} ** 매장에 대해 리뷰 등록 완료 {}", this.name, review.getId());
    }
}
