package com.calendar.calendar.service;

import com.calendar.calendar.Helper.AppointmentHelper;
import com.calendar.calendar.dto.AppointmentDto;
import com.calendar.calendar.entity.Appointment;
import com.calendar.calendar.entity.CalendarDetail;
import com.calendar.calendar.repository.AppointmentRepository;
import com.calendar.calendar.repository.CalendarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private Communication communication;

    @Override
    public Long bookAppointment(List<String> errors, AppointmentDto appointmentDto) {

        Long calendarOwnerId = validateAppointmentDetails(errors, appointmentDto);
        appointmentDto.setCalendarOwnerId(calendarOwnerId);
        if (errors.isEmpty()) {
            Appointment appointment = new Appointment();
            appointment.setCalendarId(appointmentDto.getCalendarId());
            appointment.setCalendarOwnerId(calendarOwnerId);
            appointment.setStartTime(appointmentDto.getStartTime());
            appointment.setEndTime(appointmentDto.getEndTime());
            appointment.setCreationTime(LocalDateTime.now());
            appointment.setInviteeFullName(appointmentDto.getInviteeFullName());
            appointment.setInviteeEmailAddress(appointmentDto.getInviteeEmailAddress());
            appointment.setNotesByInvitee(appointmentDto.getNotesByInvitee());
            appointment.setMeetingUrl(generateMeetingUrl(appointmentDto.getStartTime(), appointmentDto.getEndTime()));
            appointmentRepository.save(appointment);

            communication.sendCommunication(appointmentDto.getInviteeEmailAddress(), appointment.getMeetingUrl());
            appointmentDto.setMeetingUrl(appointment.getMeetingUrl());
            return appointment.getId();

        }
        return null;
    }

    private Long validateAppointmentDetails(List<String> errors, AppointmentDto appointmentDto) {
        Optional<CalendarDetail> calendarDetailOptional = calendarRepository.findById(appointmentDto.getCalendarId());
        if (calendarDetailOptional.isEmpty()) {
            errors.add("Calendar does not exists.");
        } else {
            CalendarDetail calendarDetail = calendarDetailOptional.get();
            Set<LocalDateTime> appointmentStartSlot = appointmentRepository.getAppointmentsOfCalendarOwner(calendarDetail.getCalendarOwnerId(), appointmentDto.getStartTime(), appointmentDto.getEndTime());
            if (!appointmentStartSlot.isEmpty()) {
                errors.add("This slot is already booked.");
            }
            if (appointmentDto.getStartTime().isBefore(calendarDetail.getStartTime()) || appointmentDto.getEndTime().isAfter(calendarDetail.getEndTime())){
                errors.add("Invalid time provided.");
            }
            if (!appointmentDto.getStartTime().plusMinutes(calendarDetail.getSlotDurationInMin()).isEqual(appointmentDto.getEndTime())) {
                errors.add("Invalid time provided.");
            }
            if(appointmentDto.getStartTime().isAfter(appointmentDto.getEndTime())){
                errors.add("Invalid time provided.");
            }
            if (appointmentDto.getStartTime().isBefore(LocalDateTime.now())) {
                errors.add("Invalid time provided.");
            }
            if (appointmentDto.getStartTime().getMinute() % calendarDetail.getSlotDurationInMin() != 0) {
                errors.add("Invalid time provided.");
            }
            if (appointmentDto.getEndTime().getMinute() % calendarDetail.getSlotDurationInMin() != 0) {
                errors.add("Invalid time provided.");
            }

            return calendarDetail.getCalendarOwnerId();
        }
        return null;
    }


    private String generateMeetingUrl(LocalDateTime startTime, LocalDateTime endTime) {
        //generate url by using integrated apis
        return "https://meet.google.com/";
    }

    @Override
    public List<AppointmentDto> fetchAppointments(Long calendarOwnerId) {
        return appointmentRepository.findByCalendarOwnerId(calendarOwnerId).stream()
                .filter(appointment -> appointment.getStartTime().isAfter(LocalDateTime.now()))
                .map(AppointmentHelper::convertEntityToDtoForAppointment).toList();
    }


    @Override
    public void updateAppointment(List<String> errors, AppointmentDto appointmentDto) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentDto.getId());
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setInviteeFullName(appointmentDto.getInviteeFullName());
            appointment.setNotesByInvitee(appointmentDto.getNotesByInvitee());
            appointment.setStartTime(appointmentDto.getStartTime());
            appointment.setEndTime(appointmentDto.getEndTime());
            appointment.setMeetingUrl(generateMeetingUrl(appointmentDto.getStartTime(), appointmentDto.getEndTime()));
            appointmentRepository.save(appointment);
            communication.sendCommunication(appointmentDto.getInviteeEmailAddress(), appointment.getMeetingUrl());
        } else {
            errors.add("Appointment not found.");
        }
    }

    @Override
    public void cancelAppointment(List<String> errors, Long appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointmentRepository.deleteById(appointmentId);
            communication.sendCommunication(appointment.getInviteeEmailAddress(), "canceled.");
        } else {
            errors.add("Appointment not found.");
        }
    }


}
