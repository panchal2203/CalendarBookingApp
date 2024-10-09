package com.calendar.calendar.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "calendar_detail", indexes = {
        @Index(name = "idx_user_Id", columnList = "calendarOwnerId")
})
public class CalendarDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String heading;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(nullable = false)
    private LocalTime startSlot;
    @Column(nullable = false)
    private LocalTime endSlot;
    private int slotDurationInMin;

    private LocalDateTime creationDate;

    @Column(nullable = false)
    private Long calendarOwnerId;
}
