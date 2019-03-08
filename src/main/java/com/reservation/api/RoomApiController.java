package com.reservation.api;

import com.reservation.domain.Room;
import com.reservation.service.RoomBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservationTimeTable")
public class RoomApiController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationApiController.class);

    @Autowired
    private RoomBiz roomBiz;

    @GetMapping("/lists")
    public ResponseEntity<List<Room>> getAllRoomList() {
        try {
            List<Room> roomList = roomBiz.getAllRoomList();
            if (roomList.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(roomList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
