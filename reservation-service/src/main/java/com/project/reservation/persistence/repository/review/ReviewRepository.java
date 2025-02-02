package com.project.reservation.persistence.repository.review;

import com.project.reservation.persistence.entity.Review;
import com.project.reservation.persistence.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    @Query("select avg(r.rating) from Review r where r.store.id = :storeId")
    Optional<Double> findAverageRatingByStore_Id(@Param("storeId") Long storeId);

    @Query("select r.store from Review r where r.id = :reviewId")
    Optional<Store> findStoreById(@Param("reviewId") Long reviewId);
}
