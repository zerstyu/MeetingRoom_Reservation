package com.reservation.api;

import com.google.common.base.Strings;
import com.reservation.domain.ReservationDto;
import com.reservation.domain.ReservationTable;
import com.reservation.service.ReservationBiz;
import com.reservation.service.RoomBiz;
import com.reservation.util.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationApiController {

    public static final Logger logger = LoggerFactory.getLogger(ReservationApiController.class);

    @Autowired
    private ReservationBiz reservationBiz;

    @Autowired
    private RoomBiz roomBiz;

    @GetMapping("/lists")
    public ResponseEntity<List<ReservationTable>> getAllReservationList() {
        try {
            List<ReservationTable> reservationTableList = reservationBiz.getAllReservationTableList();
            if (reservationTableList.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(reservationTableList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{roomId}/lists")
    public ResponseEntity<List<ReservationTable>> getReservationList(@PathVariable("roomId") Long roomId,
                                                                     @RequestParam("reservationDate") String reservationDate) {
        logger.info("Method : getReservationList(), param {roomId} =>" + roomId + ", param {reservationDate} =>" + reservationDate);

        if (roomId == null || Strings.isNullOrEmpty(reservationDate)) {
            logger.error("파라미터 확인을 해주세요. param {roomId} =>" + roomId + ", param {reservationDate} =>" + reservationDate);
            return new ResponseEntity(new BizException("회의실과 날짜를 확인해주세요."), HttpStatus.BAD_REQUEST);
        }

        try {
            List<ReservationTable> reservationTableList = reservationBiz.getReservationListByRoomAndDate(roomId, reservationDate);
            if (reservationTableList.isEmpty()) {
                return new ResponseEntity(reservationTableList, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(reservationTableList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<List<ReservationTable>> saveAllReservation(@RequestBody ReservationDto reservationDto) {
        try {
            List<ReservationTable> reservationTableList = reservationBiz.saveAllReservation(reservationDto);
            return new ResponseEntity(reservationTableList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
