package com.project.member.persistence.repository;

import com.project.member.persistence.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select avg(r.rating) from Review r where r.store.id = :storeId")
    Optional<Double> findAverageRatingByStore_Id(@Param("storeId") Long storeId);

}
