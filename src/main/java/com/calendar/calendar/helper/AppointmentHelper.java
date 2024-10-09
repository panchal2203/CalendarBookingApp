package com.calendar.calendar.helper;

import com.calendar.calendar.constant.ConfigConstant;
import com.calendar.calendar.dto.AppointmentDto;
import com.calendar.calendar.entity.Appointment;

public class AppointmentHelper {
    public static AppointmentDto convertEntityToDtoForAppointment(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointmentDto.getId());
        appointmentDto.setEndTime(appointment.getEndTime());
        appointmentDto.setStartTime(appointment.getStartTime());
        appointmentDto.setInviteeFullName(appointment.getInviteeFullName());
        appointmentDto.setInviteeEmailAddress(appointment.getInviteeEmailAddress());
        appointmentDto.setMeetingUrl(appointment.getMeetingUrl());
        appointmentDto.setCalendarId(appointment.getCalendarId());
        appointmentDto.setCalendarOwnerId(appointment.getCalendarOwnerId());
        appointmentDto.setTimeZone(ConfigConstant.BASE_TIME_ZONE);
        return appointmentDto;
    }
}
