package com.project.reservation.web;

import com.project.global.dto.form.CreateReviewForm;
import com.project.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<String> createReview (@RequestBody CreateReviewForm form) {
        reviewService.createReview(form);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("manager/{managerId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReviewByManager (
            @PathVariable Long managerId,
            @PathVariable Long reviewId) {
        reviewService.deleteReviewByManager(reviewId, managerId);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("customer/{customerId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReviewByCustomer (
            @PathVariable Long customerId,
            @PathVariable Long reviewId) {
        reviewService.deleteReviewByCustomer(reviewId, customerId);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("customer/reviews")
    public ResponseEntity<String> updateReview (@RequestBody CreateReviewForm form) {
        reviewService.updateReview(form);
        return ResponseEntity.ok("ok");
    }
}
