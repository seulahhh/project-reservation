package com.project.reservation.service;


import com.project.global.dto.form.AddStoreForm;
import com.project.global.dto.LocationDto;
import com.project.reservation.exception.CustomException;
import com.project.global.dto.StoreDto;
import com.project.reservation.persistence.entity.QStore;
import com.project.reservation.persistence.entity.Store;
import com.project.reservation.persistence.repository.review.ReviewRepository;
import com.project.reservation.persistence.repository.store.StoreRepository;
import com.project.reservation.util.DistanceCalculator;
import com.project.reservation.util.DtoMapper;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.reservation.exception.ErrorCode.STORE_ALREADY_REGISTERED;
import static com.project.reservation.exception.ErrorCode.STORE_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
    private final JPAQueryFactory queryFactory;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final DtoMapper dtoMapper;
    QStore qStore = QStore.store;

    /**
     * sort store
     * - 거리순
     */
    public List<StoreDto> showOrderByDistanceAsc (LocationDto locationDto) {
        NumberExpression<Double> distance = getDistance(locationDto);
        List<StoreDto> resultList =
                queryFactory.select(Projections.fields(StoreDto.class,
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
     * 이름순, (distnace정보도  포함 ver)
     */
    public List<StoreDto> showOrderByNameWithDistance (
            LocationDto locationDto) {
        NumberExpression<Double> distance = getDistance(locationDto);
        List<StoreDto> resultList =
                queryFactory.select(Projections.fields(StoreDto.class,
                                                       qStore.id, qStore.name
                                    , qStore.number, qStore.rating,
                                                       distance.as("distance")))
                                                .from(qStore)
                                                .orderBy(qStore.name.asc())
                                                .fetch();
        if (resultList.isEmpty()) {
            throw new RuntimeException("존재하는 데이터가 없습니다");
        }
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
        return storesToDtoList(resultList);
    }

    /**
     * 별점순, (distnace정보도  포함 ver)
     */
    public List<StoreDto> showOrderByRatingWithDistance (
            LocationDto locationDto) {
        NumberExpression<Double> distance = getDistance(locationDto);
        List<StoreDto> resultList =
                queryFactory.select(Projections.fields(StoreDto.class,
                                                       qStore.id, qStore.name
                                    , qStore.number, qStore.rating,
                                                       distance.as("distance")))
                                                .from(qStore)
                                                .orderBy(qStore.rating.asc())
                                                .fetch();
        if (resultList.isEmpty()) {
            throw new RuntimeException("존재하는 데이터가 없습니다");
        }
        return resultList;
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

        return storesToDtoList(resultList);
    }

    /**
     * 매장 등록하기
     * 예외처리 O
     */
    @Transactional
    public Long addStore (AddStoreForm form) {
        Long managerId = form.getManagerId();
        if (storeRepository.findStoreByManagerId(managerId)
                           .isPresent()) {
            throw new CustomException(STORE_ALREADY_REGISTERED);
        }

        Store store = Store.builder()
                .name(form.getName())
                .managerId(managerId)
                .number(form.getNumber())
                .lat(form.getLat())
                .lnt(form.getLnt())
                .build();

        Store saved = storeRepository.save(store);
        return saved.getId();
    }

    /**
     * 매장 상세 페이지 가져오기
     */
    public StoreDto getStoreDetails (Long storeId) {
        Store store = storeRepository.findById(storeId)
                                     .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        return dtoMapper.toStoreDto(store);
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
    public List<StoreDto> storesToDtoList (List<Store> stores) {
        return stores.stream()
                     .map(dtoMapper::toStoreDto)
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
                                     .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        store.setRating(storeRating);
        log.info("별점 업데이트 완료");
    }

    /**
     * 매장 id로 매장 찾기 매장 이름 반환
     */
    public Store getStoreById (Long storeId) {
        return storeRepository.findById(storeId)
                                     .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
    }

    /**
     * 매니저 Id로 매장 찾고 매장 dto 반환
     */
    public StoreDto findStoreByManagerId (Long managerId) {
        return dtoMapper.toStoreDto(storeRepository.findStoreByManagerId(managerId)
                                            .orElseThrow(() -> new CustomException(STORE_NOT_FOUND)));
    }

    /**
     * 매장 삭제하기
     */
    @Transactional
    public void deleteStore (Long storeId) {
        storeRepository.delete(storeRepository.findById(storeId)
                                              .orElseThrow(() -> new CustomException(STORE_NOT_FOUND)));
    }
}
