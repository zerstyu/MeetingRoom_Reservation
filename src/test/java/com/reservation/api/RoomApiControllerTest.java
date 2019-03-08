package com.reservation.api;

import com.google.common.collect.Lists;
import com.reservation.domain.Room;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.RoomRepository;
import com.reservation.service.RoomBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomApiController.class)
public class RoomApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoomBiz roomBiz;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @Test
    public void getAllRoomList_OK_TEST() throws Exception {
        // given
        given(roomBiz.getAllRoomList()).willReturn(getRoomList());

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/reservationTimeTable/lists")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getAllRoomList_NO_CONTENT_TEST() throws Exception {
        // given
        given(roomBiz.getAllRoomList()).willReturn(Lists.newArrayList());

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/reservationTimeTable/lists")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
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