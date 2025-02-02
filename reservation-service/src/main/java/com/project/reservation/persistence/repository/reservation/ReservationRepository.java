package com.project.reservation.persistence.repository.reservation;

import com.project.reservation.persistence.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom{

    List<Reservation> findByCustomerId(Long customerId);

    List<Reservation> findByStore_Id (Long storeId);

    List<Reservation> findByCustomerIdAndStoreIdAndReservationTimeIsAfter (Long customerId, Long storeId, LocalDateTime localDateTime);

}
