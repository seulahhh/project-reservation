package com.project.reservation.persistence.repository;

import com.project.reservation.persistence.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom{

//    List<Reservation> findByCustomer_Id (Long customerId);
//
//    List<Reservation> findByStoreIdAndCustomer_Id (Long storeId,
//            Long customerId);
//

}
