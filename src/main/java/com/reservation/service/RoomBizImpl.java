package com.reservation.service;

import com.reservation.domain.Room;
import com.reservation.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomBizImpl implements RoomBiz {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAllRoomList() {
        return roomRepository.findAll();
    }
}
