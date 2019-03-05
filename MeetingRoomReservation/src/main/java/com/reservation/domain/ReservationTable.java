package com.reservation.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "RESERVATION_TABLE")
@Data
public class ReservationTable extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESERVATION_ID")
    private Long id;

    @Version
    @Column(name = "VERSION", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;

    // 하나의 시간은 여러 개의 방으로 될 수 있음
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @Getter
    @Setter
    @Column(name = "RESERVATION_NAME", length = 20)
    private String reservationName;

    @Column(name = "IS_RESERVED", length = 20)
    private boolean isReserved;

    @Column(name = "DATE", nullable = false)
    private String date;

    @Column(name = "START_TIME", nullable = false)
    private String startTime;

    @Column(name = "END_TIME", nullable = false)
    private String endTime;
}
