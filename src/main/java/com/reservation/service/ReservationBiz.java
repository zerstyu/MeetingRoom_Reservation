package com.reservation.service;

import com.reservation.domain.ReservationDto;
import com.reservation.domain.ReservationTable;

import java.util.List;

public interface ReservationBiz {
    List<ReservationTable> getAllReservationTableList();

    List<ReservationTable> getReservationListByRoomAndDate(Long roomId, String date);

    List<ReservationTable> saveAllReservation(ReservationDto reservationDTO) throws Exception;
}
