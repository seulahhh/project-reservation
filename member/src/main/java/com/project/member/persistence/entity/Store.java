package com.project.member.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(nullable = false)
    @Builder.Default
    private Double rating = 0.0;

//    @OneToOne
//    @JoinColumn(name = "manager_id")
    @Column(name = "manager_id")
    private Long managerId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store", orphanRemoval = true)
    private List<Review> reviews;

    private String number;
    private Double lat;
    private Double lnt;

    public void addReview(Review review) {
        review.setStore(this);
        this.getReviews().add(review);
    }

    public void removeReview(Review review) {
        // todo
    }
}
