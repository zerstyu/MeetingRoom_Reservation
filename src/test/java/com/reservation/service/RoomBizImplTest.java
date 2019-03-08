package com.reservation.service;

import com.google.common.collect.Lists;
import com.reservation.domain.Room;
import com.reservation.repository.RoomRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoomBizImpl.class)
public class RoomBizImplTest {

    @Autowired
    private RoomBizImpl roomBizImpl;

    @MockBean
    private RoomRepository roomRepository;

    @Test
    public void getAllRoomList() {
        List<Room> expected = getRoomList();
        given(roomRepository.findAll()).willReturn(expected);
        List<Room> rooms = roomBizImpl.getAllRoomList();
        assertThat(rooms).isEqualTo(expected);
    }

    private List<Room> getRoomList() {
        List<Room> roomList = Lists.newArrayList();
        Room room = new Room();
        room.setRoomName("A");
        roomList.add(room);
        Room room1 = new Room();
        room1.setRoomName("B");
        roomList.add(room1);
        Room room2 = new Room();
        room2.setRoomName("C");
        roomList.add(room2);
        Room room3 = new Room();
        room3.setRoomName("D");
        roomList.add(room3);
        Room room4 = new Room();
        room4.setRoomName("E");
        roomList.add(room4);
        Room room5 = new Room();
        room5.setRoomName("F");
        roomList.add(room5);
        return roomList;
    }
}