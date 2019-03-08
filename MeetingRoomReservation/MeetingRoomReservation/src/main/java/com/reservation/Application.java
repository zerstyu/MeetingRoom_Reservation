package com.reservation;

import com.google.common.collect.Lists;
import com.reservation.domain.ReservationTable;
import com.reservation.domain.Room;
import com.reservation.repository.ReservationRepository;
import com.reservation.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public static void main(String arg[]) {
        SpringApplication.run(Application.class, arg);
    }

    @Override
    public void run(String... args) {
        List<ReservationTable> reservationTableList = getReservationTables();
        reservationRepository.saveAll(reservationTableList);
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

    private List<ReservationTable> getReservationTables() {
        List<ReservationTable> reservationTableList = Lists.newArrayList();
        List<Room> rooms = getRoomList();

        DateFormat df2 = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal2 = Calendar.getInstance();

        for (int i = 0; i < 365; i++) {
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
}
