package com.project.reservation.persistence.repository.review;

import com.project.reservation.persistence.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
//    List<Store> findByStoreId(Long storeId);
}
