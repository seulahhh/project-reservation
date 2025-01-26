package com.project.member.service;

import com.project.member.model.dto.LocationDto;
import com.project.member.model.dto.StoreDto;
import com.project.member.persistence.entity.QStore;
import com.project.member.persistence.entity.Store;
import com.project.member.persistence.repository.ReviewRepository;
import com.project.member.persistence.repository.StoreRepository;
import com.project.member.util.DistanceCalculator;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// customer가 보는 매장 위주
@Service
@RequiredArgsConstructor
public class StoreService {
    private final JPAQueryFactory queryFactory;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    QStore qStore = QStore.store; // todo 추후 테스트 과정에서 문제시 지역변수로 전환

    /**
     * sort store
     * - 거리순
     */
    public List<StoreDto> showOrderByDistanceAsc (LocationDto locationDto) {
        NumberExpression<Double> distance = getDistance(locationDto);
        List<StoreDto> resultList =
                queryFactory.select(Projections.fields(
                                    StoreDto.class,
                                    qStore.id, qStore.name
                                    , qStore.number, qStore.rating,
                                    distance.as("distance")))
                            .from(qStore)
                            .orderBy(distance.asc())
                            .fetch();

        if (resultList.isEmpty()) {
            throw new RuntimeException("존재하는 데이터가 없습니다");
        }
        // 반환된 거리값을 가지고 querydsl을 사용하여 정렬 후 List로 반환
        return resultList;
    }

    /**
     * sort store - asc
     * - 이름순
     */
    public List<StoreDto> showOrderByNameAsc () {
        List<Store> resultList = queryFactory.selectFrom(qStore)
                                             .orderBy(qStore.name.asc())
                                             .fetch();
        if (resultList.isEmpty()) {
            throw new RuntimeException("존재하는 데이터가 없습니다");
        }
        return StoresToDtoList(resultList);
    }

    /**
     * sort store
     * - 별점순
     */
    @Transactional
    public List<StoreDto> showOrderByRatingAsc () {
        List<Store> resultList = queryFactory.selectFrom(qStore)
                                             .orderBy(qStore.rating.asc())
                                             .fetch();
        if (resultList.isEmpty()) {
            throw new RuntimeException("존재하는 데이터가 없습니다");
        }
        resultList.forEach(store -> updateStoreRating(store.getId()));

        return StoresToDtoList(resultList);
    }

    /**
     * 거리 계산하기
     *
     * @param locationDto 요청으로 받은 좌표
     * @return Q클래스 반환
     */
    public NumberExpression<Double> getDistance (LocationDto locationDto) {

        return DistanceCalculator.calculate(locationDto.getLat(),
                                            locationDto.getLnt(), qStore);
    }

    /**
     * Entity -> Dto mapping
     */
    public List<StoreDto> StoresToDtoList (List<Store> stores) {
        return stores.stream()
                     .map(StoreDto::from)
                     .toList();
    }

    /**
     * 매장 별점 update
     * - 리뷰 C/U/D 할 때 호출
     * - 리뷰 별점으로 조회할 때 한번 호출
     *
     * @param storeId 매장 id
     */
    @Transactional
    public void updateStoreRating (Long storeId) {
        Double storeRating =
                reviewRepository.findAverageRatingByStore_Id(storeId)
                                .orElse(0.0);
        Store store = storeRepository.findById(storeId)
                                     .orElseThrow(() -> new RuntimeException(
                                             "Not Found Store"));

        store.setRating(storeRating);
        System.out.println("별점 업데이트 완료");
    }
}
