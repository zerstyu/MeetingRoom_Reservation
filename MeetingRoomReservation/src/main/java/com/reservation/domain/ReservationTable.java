package com.reservation.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESERVATION_TABLE")
@Data
public class ReservationTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESERVATION_ID")
    public Long id;

    @Version
    @Column(name = "VERSION", columnDefinition = "integer DEFAULT 0", nullable = false)
    public Long version;

    // 하나의 시간은 여러 개의 방으로 될 수 있음
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_ID")
    public Room room;

    @Column(name = "RESERVATION_NAME", length = 20)
    public String reservationName;

    @Column(name = "IS_RESERVED", length = 20)
    public boolean isReserved;

    @Column(name = "DATE", nullable = false)
    public String date;

    @Column(name = "START_TIME", nullable = false)
    public String startTime;

    @Column(name = "END_TIME", nullable = false)
    public String endTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_TIME", nullable = false)
    public Date createdTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_TIME", nullable = false)
    public Date updatedTime;

    public ReservationTable() {
        this.createdTime = new Date();
        this.updatedTime = new Date();
    }
}
