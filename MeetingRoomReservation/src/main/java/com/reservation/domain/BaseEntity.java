package com.reservation.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_TIME", nullable = false)
    private Date createdTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_TIME", nullable = false)
    private Date updatedTime;

    @PrePersist
    protected void onPersist() {
        this.createdTime = this.updatedTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedTime = new Date();
    }
}
