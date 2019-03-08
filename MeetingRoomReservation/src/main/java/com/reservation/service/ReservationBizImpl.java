package com.reservation.service;

import com.google.common.collect.Lists;
import com.reservation.domain.ReservationDto;
import com.reservation.domain.ReservationTable;
import com.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationBizImpl implements ReservationBiz {

    @Autowired
    private ReservationBiz reservationBiz;

    @Autowired
    private RoomBiz roomBiz;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<ReservationTable> getAllReservationTableList() {
        return reservationRepository.findAll();
    }

    @Override
    public List<ReservationTable> getReservationListByRoomAndDate(Long roomId, String date) {
        return reservationRepository.findByRoomIdAndDate(roomId, date);
    }

    @Override
    public List<ReservationTable> saveAllReservation(ReservationDto reservationDto) throws Exception {
        List<String> dateList = getDateList(reservationDto.getReservationDate(), reservationDto.getWeekCount());
        List<String> startTimeList = Lists.newArrayList(reservationDto.getStartTime().split(","));
        List<ReservationTable> reservationTableList = reservationRepository.findByRoomIdAndDateInAndStartTimeIn(
                Long.valueOf(reservationDto.getRoomId()), dateList, startTimeList);

        for (ReservationTable reservation : reservationTableList) {
            reservation.setReservationName(reservationDto.getReservationName());
            reservation.setReserved(true);
            reservationRepository.save(reservation);
        }
        return reservationTableList;
    }

    private List<String> getDateList(String date, int weekCount) {
        List<String> dateList = Lists.newArrayList();
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(convertDate(date));
        String transDate = df.format(cal.getTime());
        dateList.add(transDate);

        for (int i = 0; i < weekCount; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 7);
            String tranDate = df.format(cal.getTime());
            dateList.add(tranDate);
        }
        return dateList;
    }

    private Date convertDate(String date) {
        SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyy.mm.dd");
        SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date tempDate = null;

        try {
            tempDate = beforeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String transDate = afterFormat.format(tempDate);
        Date d = java.sql.Date.valueOf(transDate);
        return d;
    }
}
