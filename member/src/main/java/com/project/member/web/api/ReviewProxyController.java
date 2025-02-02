package com.project.member.web.api;

import com.project.global.dto.form.CreateReviewForm;
import com.project.member.client.ReviewApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewProxyController {
    private final ReviewApiClient reviewApiClient;

    /**
     * 리뷰 작성하기
     */
    @PostMapping("/customer/reviews")
    public String createReview (
            @ModelAttribute CreateReviewForm createReviewForm,
            @RequestHeader(value = HttpHeaders.REFERER, required = false) String referer) {

        reviewApiClient.callCreateReview(createReviewForm);
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "customer/customerHome";
    }

    /**
     * 리뷰 수정하기
     */
    @RequestMapping("/customer/reviews/{reviewId}/edit")
    public String updateReview (
            @PathVariable Long reviewId,
            @ModelAttribute CreateReviewForm form,
            @RequestHeader(value = HttpHeaders.REFERER, required = false) String referer) {
        form.setId(reviewId);
        reviewApiClient.callUpdateReview(form);
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "customer/customerHome";
    }

    /**
     * 리뷰삭제하기 - customer
     */
    @RequestMapping("/customer/reviews/{reviewId}/delete")
    public String deleteReviewCustomer (
            @PathVariable Long reviewId,
            @RequestHeader(value = HttpHeaders.REFERER, required = false) String referer) {

        reviewApiClient.callDeleteReview("ROLE_CUSTOMER", reviewId);
        log.info("referer: {}", referer);
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "customer/customerHome";
    }

    /**
     * 리뷰삭제하기 - manager
     */
    @RequestMapping("/manager/reviews/{reviewId}/delete")
    public String deleteReviewManager (
            @PathVariable Long reviewId,
            @RequestHeader(value = HttpHeaders.REFERER, required = false) String referer) {

        reviewApiClient.callDeleteReview("ROLE_MANAGER", reviewId);
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "manager/managerHome";
    }
}
