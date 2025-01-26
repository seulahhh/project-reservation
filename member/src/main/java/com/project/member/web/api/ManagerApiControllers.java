package com.project.member.web.api;

import com.project.member.model.dto.ReviewDto;
import com.project.member.model.dto.form.AddStoreForm;
import com.project.member.service.ManagerService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/store")
@RequiredArgsConstructor
public class ManagerApiControllers {
    private final EntityManager entityManager;
    private final ManagerService storeService;
    // manager
    // 매장 등록하기
    @PostMapping
    public ResponseEntity<String> addStore (@RequestBody AddStoreForm form) {
        String message = String.format("'%s' 매장이 성공적으로 등록되었습니다!", storeService.addStore(form).getBody());
        return ResponseEntity.ok(message);
    }

    // 매장 삭제하기
    @DeleteMapping
    public ResponseEntity<String> deleteStore (@RequestParam Long storeId) {
        String message = String.format("'%s' 매장이 성공적으로 삭제되었습니다!", storeService.deleteStore(storeId).getBody());
        return ResponseEntity.ok(message);
    }
    // 매장 수정하기
    // todo 추후 수정 기능 필요시 추가

    // 내매장 리뷰 보기
    @GetMapping("/review")
    public ResponseEntity<List<ReviewDto>> showMyStoreReviews (@RequestParam Long managerId) {
        return ResponseEntity.ok(storeService.showMyStoreReviews(managerId));
    }

    // 리뷰 삭제하기
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<Long> deleteReview (@PathVariable Long reviewId) {
        storeService.deleteReview(reviewId);
        return ResponseEntity.ok(reviewId);
    }


}
