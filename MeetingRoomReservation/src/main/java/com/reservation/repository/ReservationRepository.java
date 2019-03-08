package com.reservation.repository;

import com.reservation.domain.ReservationTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationTable, Long> {
    List<ReservationTable> findAll();

    List<ReservationTable> findByRoomIdAndDate(Long roomId, String reservationDate);

    List<ReservationTable> findByRoomIdAndDateInAndStartTimeIn(Long roomId, List<String> date, List<String> startTimeList);
}
