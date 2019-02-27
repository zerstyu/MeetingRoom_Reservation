package com.reservation.api;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.reservation.domain.ReservationDto;
import com.reservation.domain.ReservationTable;
import com.reservation.domain.Room;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.RoomRepository;
import com.reservation.service.ReservationBiz;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationApiController.class)
public class ReservationApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationBiz reservationBiz;

    @MockBean
    private RoomBiz roomBiz;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @Test
    public void getAllReservationList_OK_TEST() throws Exception {
        // given
        given(reservationBiz.getAllReservationTableList()).willReturn(getReservationTables());

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/reservation/lists")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getAllReservationList_NO_CONTENT_TEST() throws Exception {
        // given
        given(reservationBiz.getAllReservationTableList()).willReturn(Lists.newArrayList());

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/reservation/lists")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void getReservationList_OK_TEST() throws Exception {
        // given
        List<ReservationTable> reservationTableList = Lists.newArrayList();
        reservationTableList.add(getReservationTable());
        given(reservationBiz.getReservationListByRoomAndDate(1L, "2019.03.31")).willReturn(reservationTableList);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/reservation/1/lists?reservationDate=2019.03.31")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getReservationList_NO_CONTENT_TEST() throws Exception {
        // given
        List<ReservationTable> reservationTableList = Lists.newArrayList();
        reservationTableList.add(getReservationTable());
        given(reservationBiz.getReservationListByRoomAndDate(1L, "2019.03.31")).willReturn(reservationTableList);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/reservation/1/lists?reservationDate=2019.03.30")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void getReservationList_BAD_REQUEST_TEST() throws Exception {
        // given
        List<ReservationTable> reservationTableList = Lists.newArrayList();
        reservationTableList.add(getReservationTable());
        given(reservationBiz.getReservationListByRoomAndDate(1L, "2019.03.31")).willReturn(reservationTableList);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/reservation/1/lists?reservationDate=")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void saveAllReservation() throws Exception {
        // given
        List<ReservationTable> reservationTableList = Lists.newArrayList();
        reservationTableList.add(getReservationTable());
        given(reservationBiz.saveAllReservation(getReservationDto())).willReturn(reservationTableList);

        JsonObject reservationDto = new JsonObject();
        reservationDto.addProperty("roomId", "1");
        reservationDto.addProperty("reservationName", "test");
        reservationDto.addProperty("reservationDate", "2019.03.31");
        reservationDto.addProperty("startTime", "00:00");
        reservationDto.addProperty("weekCount", 1);

        mvc.perform(
                post("/api/reservation/save")
                        .contentType("application/json")
                        .content(reservationDto.toString())).andExpect(status().isOk());
    }

    private ReservationDto getReservationDto() {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.roomId = "1";
        reservationDto.reservationName = "test";
        reservationDto.reservationDate = "2019.03.31";
        reservationDto.startTime = "00:00";
        reservationDto.weekCount = 1;
        return reservationDto;
    }

    private Room getRoom() {
        Room room = new Room();
        room.id = 1L;
        room.roomName = "TESTROOM";
        room.createdTime = new Date();
        room.updatedTime = new Date();
        return room;
    }

    private ReservationTable getReservationTable() {
        ReservationTable reservationTable = new ReservationTable();
        reservationTable.id = 1L;
        reservationTable.version = 0L;
        reservationTable.reservationName = "TEST";
        reservationTable.isReserved = false;
        reservationTable.date = "2019.03.31";
        reservationTable.startTime = "00:00";
        reservationTable.endTime = "00:30";
        reservationTable.createdTime = new Date();
        reservationTable.updatedTime = new Date();
        reservationTable.room = getRoom();
        return reservationTable;
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

    private List<ReservationTable> getReservationTables() {
        List<ReservationTable> reservationTableList = Lists.newArrayList();
        List<Room> rooms = getRoomList();

        DateFormat df2 = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal2 = Calendar.getInstance();

        for (int i = 0; i < 1; i++) {
            for (Room room : rooms) {
                DateFormat df = new SimpleDateFormat("HH:mm");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);

                Calendar cal1 = Calendar.getInstance();
                cal1.set(Calendar.HOUR_OF_DAY, 0);
                cal1.set(Calendar.MINUTE, 30);
                cal1.set(Calendar.SECOND, 0);

                int startDate = cal.get(Calendar.DATE);
                while (cal.get(Calendar.DATE) == startDate) {
                    ReservationTable reservationTable = new ReservationTable();
                    reservationTable.room = room;
                    reservationTable.date = df2.format(cal2.getTime());
                    reservationTable.startTime = df.format(cal.getTime());
                    reservationTable.endTime = df.format(cal1.getTime());
                    cal.add(Calendar.MINUTE, 30);
                    cal1.add(Calendar.MINUTE, 30);
                    reservationTableList.add(reservationTable);
                }
            }
            cal2.add(Calendar.DAY_OF_MONTH, 1);
        }

        return reservationTableList;
    }
}