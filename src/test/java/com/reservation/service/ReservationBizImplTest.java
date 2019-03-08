package com.reservation.service;

import com.google.common.collect.Lists;
import com.reservation.domain.ReservationDto;
import com.reservation.domain.ReservationTable;
import com.reservation.domain.Room;
import com.reservation.repository.ReservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReservationBizImpl.class)
public class ReservationBizImplTest {

    @Autowired
    private ReservationBizImpl reservationBizImpl;

    @MockBean
    private RoomBiz roomBiz;

    @MockBean
    private ReservationRepository reservationRepository;

    @Test
    public void getAllReservationTableList_equalTest() {
        List<ReservationTable> expected = getReservationTables();
        given(reservationRepository.findAll()).willReturn(expected);
        List<ReservationTable> reservationTableList = reservationBizImpl.getAllReservationTableList();
        assertThat(reservationTableList).isEqualTo(expected);
    }

    @Test
    public void getAllReservationTableList_nullTest() {
        List<ReservationTable> reservationTableList = reservationBizImpl.getAllReservationTableList();
        assertThat(reservationTableList).isNotNull();
    }

    @Test
    public void getReservationListByRoomAndDate() {
        List<ReservationTable> expected = Lists.newArrayList();
        expected.add(getReservationTable(1));
        given(reservationRepository.findByRoomIdAndDate(1L, "2019-03-31")).willReturn(expected);
        List<ReservationTable> reservationTableList = reservationBizImpl.getReservationListByRoomAndDate(1L, "2019-03-31");
        assertThat(reservationTableList).isEqualTo(expected);
    }

    @Test
    public void getReservationListByRoomAndDate_NullTest() {
        List<ReservationTable> expected = Lists.newArrayList();
        given(reservationRepository.findByRoomIdAndDate(1L, "2019-03-31")).willReturn(expected);
        List<ReservationTable> reservationTableList = reservationBizImpl.getReservationListByRoomAndDate(1L, "2019-03-31");
        assertTrue(reservationTableList.isEmpty());
    }

    @Test
    public void saveAllReservation() throws Exception {
        List<ReservationTable> expected = Lists.newArrayList();
        expected.add(getReservationTable(1));
        given(reservationRepository.save(getReservationTable(1))).willReturn(getReservationTable(1));
        List<ReservationTable> reservationTableList = reservationBizImpl.saveAllReservation(getReservationDto(1));
        assertThat(reservationTableList).isNotEqualTo(expected);
    }

    @Test
    public void saveAllReservation2() throws Exception {
        List<ReservationTable> expected = Lists.newArrayList();
        ;
        expected.add(getReservationTable(1));
        expected.add(getReservationTable(2));
        given(reservationRepository.save(getReservationTable(1))).willReturn(getReservationTable(1));
        given(reservationRepository.findByRoomIdAndDateInAndStartTimeIn(1L, getDateList(), getStartTimeList())).willReturn(expected);

        List<ReservationTable> reservationTableList = reservationBizImpl.saveAllReservation(getReservationDto(2));
        assertThat(reservationTableList).isEqualTo(expected);
    }

    private ReservationTable getReservationTable(int testcase) {
        ReservationTable reservationTable = new ReservationTable();
        switch (testcase) {
            case 1:
                reservationTable.setReservationName("TEST");
                reservationTable.setReserved(false);
                reservationTable.setDate("2019.03.31");
                reservationTable.setStartTime("00:00");
                reservationTable.setEndTime("00:30");
                reservationTable.setRoom(getRoom());
                break;
            case 2:
                reservationTable.setReservationName("TEST");
                reservationTable.setReserved(false);
                reservationTable.setDate("2019.04.07");
                reservationTable.setStartTime("00:00");
                reservationTable.setEndTime("00:30");
                reservationTable.setRoom(getRoom());
                break;
        }
        return reservationTable;
    }

    private ReservationDto getReservationDto(int testcase) {
        ReservationDto reservationDto = new ReservationDto();
        switch (testcase) {
            case 1:
                reservationDto.setRoomId("1");
                reservationDto.setReservationName("test");
                reservationDto.setReservationDate("2019.03.31");
                reservationDto.setStartTime("00:00");
                reservationDto.setWeekCount(0);
                break;
            case 2:
                reservationDto.setRoomId("1");
                reservationDto.setReservationName("test");
                reservationDto.setReservationDate("2019.03.31");
                reservationDto.setStartTime("00:00");
                reservationDto.setWeekCount(1);
                break;
        }
        return reservationDto;
    }

    private List<String> getStartTimeList() {
        List<String> startTimeList = Lists.newArrayList();
        startTimeList.add("00:00");
        return startTimeList;
    }

    private List<String> getDateList() {
        List<String> dateList = Lists.newArrayList();
        dateList.add("2019.03.31");
        dateList.add("2019.04.07");
        return dateList;
    }

    private Room getRoom() {
        Room room = new Room();
        room.setRoomName("TESTROOM");
        return room;
    }

    private List<ReservationTable> getReservationTables() {
        List<ReservationTable> reservationTableList = Lists.newArrayList();
        List<Room> rooms = getRoomList();

        DateFormat df2 = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal2 = Calendar.getInstance();

        for (int i = 0; i < 10; i++) {
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
                    reservationTable.setRoom(room);
                    reservationTable.setDate(df2.format(cal2.getTime()));
                    reservationTable.setStartTime(df.format(cal.getTime()));
                    reservationTable.setEndTime(df.format(cal1.getTime()));
                    cal.add(Calendar.MINUTE, 30);
                    cal1.add(Calendar.MINUTE, 30);
                    reservationTableList.add(reservationTable);
                }
            }
            cal2.add(Calendar.DAY_OF_MONTH, 1);
        }

        return reservationTableList;
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