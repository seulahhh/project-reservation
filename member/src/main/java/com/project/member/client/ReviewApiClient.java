package com.project.member.client;

import com.project.global.dto.ReviewDto;
import com.project.global.dto.form.CreateReviewForm;
import com.project.member.service.CustomerService;
import com.project.member.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ReviewApiClient {
    private final WebClient webClient;
    private final CustomerService customerService;
    private final ManagerService managerService;

    /**
     * 리뷰 작성 요청 api 호출
     */
    public String callCreateReview (CreateReviewForm createReviewForm) {
        webClient.post()
                 .uri("/api/reviews")
                 .header("Content-Type", "application/json")
                 .bodyValue(createReviewForm)
                 .retrieve()
                 .bodyToMono(ReviewDto.class)
                 .block();
        return "ok";
    }

    /**
     * 리뷰 수정 요청 api 호출
     */
    public String callUpdateReview (CreateReviewForm createReviewForm) {
        Long reviewId = createReviewForm.getId();
        String res = webClient.put()
                              .uri("/api/customer/reviews")
                              .header("Content-Type", "application/json")
                              .bodyValue(createReviewForm)
                              .retrieve()
                              .bodyToMono(String.class)
                              .block();
        return res;
    }

    /**
     * 리뷰 삭제 요청 api 호출
     */
    public String callDeleteReview (String role, Long reviewId) {
        String uri;
        Long userId;
        if (role.equals("ROLE_MANAGER")) {
            userId = managerService.getManagerId();
            uri = "/api/manager/" + userId + "/reviews/" + reviewId;
        } else {
            userId = customerService.getCurrentCustomerId();
            uri = "/api/customer/" + userId + "/reviews/" + reviewId;
        }
        String res = webClient.delete()
                              .uri(uri)
                              .retrieve()
                              .bodyToMono(String.class)
                              .block();
        return res;
    }
}
