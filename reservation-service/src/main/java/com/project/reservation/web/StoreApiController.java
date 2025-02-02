package com.project.reservation.web;

import com.project.global.dto.LocationDto;
import com.project.global.dto.StoreDto;
import com.project.global.dto.form.AddStoreForm;
import com.project.reservation.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.Location;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreApiController {
    private final StoreService storeService;
    // customer
    @Operation(
            summary = "매장 리스트를 거리순으로 조회하기"
    )
    @GetMapping("/stores/sort-by-distance")
    public ResponseEntity<List<StoreDto>> getStoreListOrderByDistance(
            @RequestParam Double lat, @RequestParam Double lnt) {
        List<StoreDto> list = storeService.showOrderByDistanceAsc(LocationDto.of(lat, lnt));
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "매장 리스트를 이름순으로 조회하기"
    )
    @GetMapping("/stores/sort-by-name")
    public ResponseEntity<List<StoreDto>> getStoreListOrderByName(
            @RequestParam Double lat, @RequestParam Double lnt) {
        List<StoreDto> list = storeService.showOrderByNameWithDistance(LocationDto.of(lat, lnt));
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "매장 리스트를 별점순으로 조회하기"
    )
    @GetMapping("/stores/sort-by-rating")
    public ResponseEntity<List<StoreDto>> getStoreListOrderByRating(
            @RequestParam Double lat, @RequestParam Double lnt) {
        List<StoreDto> list = storeService.showOrderByRatingWithDistance(LocationDto.of(lat, lnt));
        return ResponseEntity.ok(list);
    }

    @Operation(
            summary = "매장 상세 정보 조회하기",
            description = "store id를 PathVariable 로 받아, 해당하는 매장의 상세 정보(매장명 부터 리뷰리스트 등)를 가져옵니다"
    )
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<StoreDto> getStoreDetail(@PathVariable Long storeId) {
        StoreDto storeDetails = storeService.getStoreDetails(storeId);
        return ResponseEntity.ok(storeDetails);
    }

    // manager
    @Operation(
            summary = "매니저가 매장을 등록하는 API"
    )
    @PostMapping("/stores")
    public ResponseEntity<Long> registerStore (@RequestBody AddStoreForm form) {
        Long storeId = storeService.addStore(form);
        return ResponseEntity.ok(storeId);
    }

    @Operation(
            summary = "매니저가 등록한 매장을 삭제하는 API"
    )
    @DeleteMapping("/stores/{storeId}")
    public ResponseEntity<?> deleteStore (@PathVariable Long storeId) {
        return null;
    }

}
