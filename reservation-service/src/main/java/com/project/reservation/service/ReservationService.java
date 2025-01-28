package com.project.reservation.service;

import com.project.member.model.dto.StoreDto;
import com.project.member.persistence.entity.Store;
import com.project.member.persistence.repository.CustomerRepository;
import com.project.member.persistence.repository.StoreRepository;
import com.project.reservation.model.dto.CreateReservationForm;
import com.project.reservation.model.dto.ReservationDto;
import com.project.reservation.persistence.entity.Reservation;
import com.project.reservation.persistence.repository.ReservationRepository;
import com.project.reservation.util.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

//    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
//    private final StoreRepository storeRepository;
    private final ReservationMapper reservationMapper;

    // 예약 생성하기
    @Transactional
    public ReservationDto createReservation (CreateReservationForm form) {
        getReservationAvailability(); // 예외처리 여기서 끝냄
        Reservation reservation = reservationMapper.toEntity(form);
        return reservationMapper.toReservationDto(reservationRepository.save(reservation));
    }

    // 예약 내역 보기(상태 포함)
    public void viewReservations () {
        // todo 수행 완료한 예약은 리뷰를 작성할 수 있게 하기

    }

    // 키오스크에서 도착 여부 확인 하기
    public void checkInAtKiosk () {
        // customer id를 통해 해당 매장에 예약한 내역 찾기
        // 도착 확인 버튼 누르기
        // reservation의 상태 바꾸기(arrival상태)
    }

    // ------------------------------------------ 비즈니스로직 끝

    // 예약 가능한지 유효성 검사
    // todo
    public boolean getReservationAvailability () {

        // 예외: 같은 매장에 같은 날 예약이 있으면 안된다.
        // 예약 가능한지 여부를 먼저 확인해야 한다.
        // ↪︎ Reservation테이블에서 사용자가 원하는 시간대에 예약이 있는지 확인 -> '있으면' 예약불가
        // ↪︎ StoreAvailability 테이블 내에 사용자가 원하는 예약 조건이 있는지 확인 -> '없으면' 예약 불가,
        // ↪︎ StoreAvailability 테이블의 하루 예약 가능 수와, Reservation테이블에서 해당 매장의 해당
        // 날짜를 연산해서 자리수가 남아있으면 예약 가능
        return true;
    }

    // 받은 storeId로 예약 store 찾기
//    public StoreDto getStoreDtoByStoreId (Long storeId) {
//        Store store = storeRepository.findById(storeId)
//                              .orElseThrow(() -> new RuntimeException("해당 매장이 존재하지 " + "않습니다" + ".")); // todo
//        return StoreDto.from(store);
//    }
    // 맞는 예약 내역 조회 (DB)
}
