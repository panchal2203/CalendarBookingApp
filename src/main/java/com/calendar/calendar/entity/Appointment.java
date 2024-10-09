package com.calendar.calendar.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointment_detail")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long calendarId;

    @Column(nullable = false)
    private Long calendarOwnerId;

    @Column(nullable = false)
    private String inviteeEmailAddress;
    private String inviteeFullName;
    private String notesByInvitee;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;
    private LocalDateTime creationTime;
    private String meetingUrl;
}
