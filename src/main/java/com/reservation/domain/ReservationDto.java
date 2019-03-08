package com.reservation.domain;

import lombok.Data;

@Data
public class ReservationDto {
    private String roomId;
    private String reservationName;
    private String reservationDate;
    private String startTime;
    private int weekCount;
}
