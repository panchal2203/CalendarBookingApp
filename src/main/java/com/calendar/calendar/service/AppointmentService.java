package com.calendar.calendar.service;

import com.calendar.calendar.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {
    Long bookAppointment(List<String> errors, AppointmentDto appointmentDto);
    List<AppointmentDto> fetchAppointments(Long calendarOwnerId);
    void updateAppointment(List<String> errors, AppointmentDto appointmentDto);
    void cancelAppointment(List<String> errors, Long appointmentId);
}
