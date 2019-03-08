package com.reservation.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ROOM")
@Data
public class Room extends BaseEntity {
    @Column(name = "ROOM_NAME", nullable = false, length = 10)
    private String roomName;
}
