package com.calendar.calendar.controller;

import com.calendar.calendar.dto.ApiResponse;
import com.calendar.calendar.dto.AppointmentDto;
import com.calendar.calendar.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/{calendarOwnerId}/appointments")
    public ResponseEntity<ApiResponse<?>> fetchAppointments(@PathVariable Long calendarOwnerId) {
        ApiResponse<?> response;
        try {
            List<AppointmentDto> appointments = appointmentService.fetchAppointments(calendarOwnerId);
            response = new ApiResponse<>("success", appointments);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ApiResponse<>("some error occurred.");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> bookAppointment(@RequestBody AppointmentDto appointmentDto) {
        ApiResponse<?> response;
        List<String> errors = new ArrayList<>();
        try {
            appointmentDto.updateTimeObjectsToBaseZone();
            Long id = appointmentService.bookAppointment(errors, appointmentDto);
            appointmentDto.setId(id);
            if (errors.isEmpty()) {
                response = new ApiResponse<>("success", appointmentDto);
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
    public ResponseEntity<ApiResponse<?>> updateAppointment(@RequestBody AppointmentDto appointmentDto) {
        ApiResponse<?> response;
        List<String> errors = new ArrayList<>();
        try {
            appointmentDto.updateTimeObjectsToBaseZone();
            appointmentService.updateAppointment(errors, appointmentDto);
            if (errors.isEmpty()) {
                response = new ApiResponse<>("success", appointmentDto);
            } else {
                response = new ApiResponse<>(errors.get(0));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response = new ApiResponse<>("some error occurred.");
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<?>> cancelAppointment(@PathVariable Long appointmentId) {
        ApiResponse<?> response;
        List<String> errors = new ArrayList<>();
        try {
            appointmentService.cancelAppointment(errors, appointmentId);
            if (errors.isEmpty()) {
                response = new ApiResponse<>("appointment canceled.", null);
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
