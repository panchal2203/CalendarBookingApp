package com.calendar.calendar.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeSlot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
