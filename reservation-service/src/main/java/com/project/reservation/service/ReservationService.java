package com.project.reservation.service;

import com.project.reservation.model.dto.CreateReservationForm;
import com.project.reservation.model.dto.ReservationDto;
import com.project.reservation.model.types.ReservationStatus;
import com.project.reservation.persistence.entity.Reservation;
import com.project.reservation.persistence.repository.ReservationRepository;
import com.project.reservation.util.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    /**
     * 요청받은 예약을 DB 저장
     */
    @Transactional
    public ReservationDto createReservation (CreateReservationForm form) {
        getReservationAvailability(); // 예외처리 여기서 끝냄
        Reservation reservation = reservationMapper.toEntity(form);
        return reservationMapper.toReservationDto(reservationRepository.save(reservation));
    }

    /**
     * Manager에게 예약 승인 요청 알림 보내기
     */
    public void notifyManagerOfRequest() {

    }
    /**
     * Manager의 승인 상태 변경에 따라
     * 예약상태 업데이트하기
     */
    public boolean updateConfirmStatus(Long reservationId, ReservationStatus status) {
        Reservation reservation = getReservation(reservationId);
        if(reservation.getReservationStatus() == status) {
            throw new RuntimeException("이미 처리된 요청입니다"); // todo
        }
        reservation.setReservationStatus(status);
        return true;
    }
    /**
     * 요청받은 예약정보 넘기기(Customer에게)
     */
    public ReservationDto viewReservations (Long customerId) {
        Reservation reservation = reservationRepository.findByCustomerId(customerId)
                                                       .orElseThrow(() -> new RuntimeException());
        return reservationMapper.toReservationDto(reservation);
    }

    /**
     * ReservationStatus 업데이트
     */
    @Transactional
    public boolean updateArrivalStatus (Long reservationId) {
        if (getReservation(reservationId).isHasArrived()) {
            throw new RuntimeException("이미 도착 확인이 되었습니다");
        }
        //
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

    // 예약 id로 예약 Entity 찾기
    public Reservation getReservation (Long id) {
        return reservationRepository.findById(id)
                                                       .orElseThrow(() -> new RuntimeException());
    }

    // 받은 storeId로 예약 store 찾기
//    public StoreDto getStoreDtoByStoreId (Long storeId) {
//        Store store = storeRepository.findById(storeId)
//                              .orElseThrow(() -> new RuntimeException("해당 매장이 존재하지 " + "않습니다" + ".")); // todo
//        return StoreDto.from(store);
//    }
    // 맞는 예약 내역 조회 (DB)
}
