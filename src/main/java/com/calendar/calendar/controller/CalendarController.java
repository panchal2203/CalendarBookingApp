package com.calendar.calendar.controller;

import com.calendar.calendar.dto.ApiResponse;
import com.calendar.calendar.dto.CalendarDto;
import com.calendar.calendar.dto.TimeSlot;
import com.calendar.calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;



    @PostMapping
    public ResponseEntity<ApiResponse<?>> scheduleCalendar(@RequestBody CalendarDto calendarDto) {
        ApiResponse<?> response;
        List<String> errors = new ArrayList<>();
        try {
            calendarDto.updateTimeObjectsToBaseZone();
            calendarDto.setId(calendarService.scheduleCalendar(errors, calendarDto));
            if (errors.isEmpty()) {
                response = new ApiResponse<>("success", calendarDto);
            } else {
                response = new ApiResponse<>(errors.get(0));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ApiResponse<>("some error occurred.");
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<?>> updateCalendar(@RequestBody CalendarDto calendarDto) {
        ApiResponse<?> response;
        List<String> errors = new ArrayList<>();
        try {
            calendarDto.updateTimeObjectsToBaseZone();
            calendarService.updateCalendar(errors, calendarDto);
            if (errors.isEmpty()) {
                response = new ApiResponse<>("success", calendarDto);
            } else {
                response = new ApiResponse<>(errors.get(0));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ApiResponse<>("some error occurred.");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{calendarId}")
    public ResponseEntity<ApiResponse<?>> fetchCalendarDetails(@PathVariable Long calendarId) {
        ApiResponse<?> response;
        List<String> errors = new ArrayList<>();
        try {
           
            CalendarDto calendarDto = calendarService.fetchCalendarDetails(errors, calendarId);
            if (errors.isEmpty()) {
                response = new ApiResponse<>("success", calendarDto);
            } else {
                response = new ApiResponse<>(errors.get(0));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ApiResponse<>("some error occurred.");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{calendarId}/availableSlots")
    public ResponseEntity<ApiResponse<?>> fetchAvailableSlots(@PathVariable Long calendarId, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        ApiResponse<?> response;
        List<String> errors = new ArrayList<>();
        try {
            List<TimeSlot> timeSlots = calendarService.fetchAvailableSlots(errors, calendarId, startTime, endTime);
            if (errors.isEmpty()) {
                response = new ApiResponse<>("success", timeSlots);
            } else {
                response = new ApiResponse<>(errors.get(0));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ApiResponse<>("some error occurred.");
        }
        return ResponseEntity.ok(response);

    }



}
