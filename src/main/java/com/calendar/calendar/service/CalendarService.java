package com.calendar.calendar.service;

import com.calendar.calendar.dto.CalendarDto;
import com.calendar.calendar.dto.TimeSlot;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarService {
    Long scheduleCalendar(List<String> errors, CalendarDto calendarDto);

    List<TimeSlot> fetchAvailableSlots(List<String> errors, Long calendarId, LocalDateTime startTime, LocalDateTime endTime);

    void updateCalendar(List<String> errors, CalendarDto calendarDto);

    CalendarDto fetchCalendarDetails(List<String> errors, Long calendarId);
}
