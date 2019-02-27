package com.reservation.controller;

import com.google.common.collect.Lists;
import com.reservation.domain.Room;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.RoomRepository;
import com.reservation.service.RoomBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private ReservationController reservationController;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private RoomBiz roomBiz;

    @Test
    public void home() {
        ModelAndView expected = new ModelAndView();
        expected.addObject("rooms", getRoomList());
        expected.setViewName("/home.html");

        when(roomBiz.getAllRoomList()).thenReturn(getRoomList());

        ModelAndView modelAndView = reservationController.home();
        assertEquals(expected.getViewName(), modelAndView.getViewName());
    }


    private List<Room> getRoomList() {
        List<Room> roomList = Lists.newArrayList();
        Room room = new Room("A");
        roomList.add(room);
        Room room1 = new Room("B");
        roomList.add(room1);
        Room room2 = new Room("C");
        roomList.add(room2);
        Room room3 = new Room("D");
        roomList.add(room3);
        Room room4 = new Room("E");
        roomList.add(room4);
        Room room5 = new Room("F");
        roomList.add(room5);
        return roomList;
    }
}