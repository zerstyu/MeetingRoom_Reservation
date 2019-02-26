package com.reservation.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ROOM")
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROOM_ID")
    public Long id;

    @Column(name = "ROOM_NAME", nullable = false, length = 10)
    public String roomName;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_TIME", nullable = false)
    public Date createdTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_TIME", nullable = false)
    public Date updatedTime;

    public Room() {
        this.createdTime = new Date();
        this.updatedTime = new Date();
    }

    public Room(String roomName) {
        this.roomName = roomName;
        this.createdTime = new Date();
        this.updatedTime = new Date();
    }
}
