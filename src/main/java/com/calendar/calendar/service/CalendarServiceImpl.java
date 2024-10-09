package com.calendar.calendar.service;

import com.calendar.calendar.Helper.AppointmentHelper;
import com.calendar.calendar.constant.ConfigConstant;
import com.calendar.calendar.dto.AppointmentDto;
import com.calendar.calendar.dto.CalendarDto;
import com.calendar.calendar.dto.TimeSlot;
import com.calendar.calendar.entity.CalendarDetail;
import com.calendar.calendar.entity.User;
import com.calendar.calendar.repository.AppointmentRepository;
import com.calendar.calendar.repository.CalendarRepository;
import com.calendar.calendar.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    @Autowired
    private UserRepository userRepository;


    @Override
    public Long scheduleCalendar(List<String> errors, CalendarDto calendarDto) {
        Optional<User> userOptional = userRepository.findById(calendarDto.getCalendarOwnerId());
        if (userOptional.isEmpty()) {
            errors.add("User does not exists.");
        } else {
            validateTimes(errors, calendarDto);
            if (errors.isEmpty()) {
                CalendarDetail calendar = new CalendarDetail();
                calendar.setCalendarOwnerId(calendarDto.getCalendarOwnerId());
                calendar.setHeading(calendarDto.getHeading());
                calendar.setDescription(calendarDto.getDescription());
                calendar.setCreationDate(LocalDateTime.now());
                calendar.setSlotDurationInMin(ConfigConstant.SLOT_DURATION);
                calendar.setStartSlot(calendarDto.getStartSlot());
                calendar.setEndSlot(calendarDto.getEndSlot());
                calendar.setStartTime(calendarDto.getStartTime());
                calendar.setEndTime(calendarDto.getEndTime());
                calendarRepository.save(calendar);
                return calendar.getId();
            }
        }
        return null;
    }

    private void validateTimes(List<String> errors, CalendarDto calendarDto) {
        if (calendarDto.getStartTime() == null || calendarDto.getEndTime() == null || calendarDto.getStartSlot() == null || calendarDto.getEndSlot() == null) {
            errors.add("Mandatory fields are missing.");
        } else if (!calendarDto.getStartTime().isBefore(calendarDto.getEndTime()) || !calendarDto.getStartSlot().isBefore(calendarDto.getEndSlot())) {
            errors.add("Invalid time provided.");
        }

    }

    @Override
    public List<TimeSlot> fetchAvailableSlots(List<String> errors, Long calendarId, LocalDateTime startTime, LocalDateTime endTime) {
        Optional<CalendarDetail> optionalCalendarDetail = calendarRepository.findById(calendarId);
        List<TimeSlot> availableTimeSlot = new ArrayList<>();
        if (optionalCalendarDetail.isPresent()) {
            CalendarDetail calendar = optionalCalendarDetail.get();
            Set<LocalDateTime> appointmentStartSlot = appointmentRepository.getAppointmentsOfCalendarOwner(calendar.getCalendarOwnerId(), startTime, endTime);
            int slotDuration = calendar.getSlotDurationInMin();
            if (startTime.isBefore(calendar.getStartTime())) {
                startTime = calendar.getStartTime();
            }
            if (endTime.isAfter(calendar.getEndTime())) {
                endTime = calendar.getEndTime();
            }
            while (startTime.isBefore(endTime)) {
                if (!appointmentStartSlot.contains(startTime)) {
                    availableTimeSlot.add(new TimeSlot(startTime, startTime.plusMinutes(slotDuration)));
                }
                startTime = startTime.plusMinutes(slotDuration);
            }
        } else {
            errors.add("calendar does not exists.");
        }
        return availableTimeSlot;
    }


    @Override
    public void updateCalendar(List<String> errors, CalendarDto calendarDto) {
        Optional<CalendarDetail> optionalCalendarDetail = calendarRepository.findById(calendarDto.getId());
        if (optionalCalendarDetail.isPresent()) {
            CalendarDetail calendar = optionalCalendarDetail.get();
            calendar.setHeading(calendarDto.getHeading());
            calendar.setDescription(calendarDto.getDescription());
            calendar.setStartSlot(calendarDto.getStartSlot());
            calendar.setEndSlot(calendarDto.getEndSlot());
            calendar.setStartTime(calendarDto.getStartTime());
            calendar.setEndTime(calendarDto.getEndTime());
            calendarRepository.save(calendar);
        } else {
            errors.add("calendar does not exists.");
        }
    }


    @Override
    public CalendarDto fetchCalendarDetails(List<String> errors, Long calendarId) {
        CalendarDto calendarDto = null;
        Optional<CalendarDetail> optionalCalendarDetail = calendarRepository.findById(calendarId);
        if (optionalCalendarDetail.isPresent()) {
            CalendarDetail calendarDetail = optionalCalendarDetail.get();
            calendarDto = getCalendarDto(calendarDetail);
        } else {
            errors.add("calendar does not exists.");
        }
        return calendarDto;

    }

    private CalendarDto getCalendarDto(CalendarDetail calendarDetail) {
        CalendarDto calendarDto = new CalendarDto();
        calendarDto.setDescription(calendarDetail.getDescription());
        calendarDto.setHeading(calendarDetail.getHeading());
        calendarDto.setStartSlot(calendarDetail.getStartSlot());
        calendarDto.setEndSlot(calendarDetail.getEndSlot());
        calendarDto.setId(calendarDetail.getId());
        calendarDto.setStartTime(calendarDetail.getStartTime());
        calendarDto.setEndTime(calendarDetail.getEndTime());
        calendarDto.setSlotDurationInMin(ConfigConstant.SLOT_DURATION);
        calendarDto.setCalendarOwnerId(calendarDetail.getCalendarOwnerId());
        List<AppointmentDto> appointments = appointmentRepository.findByCalendarId(calendarDetail.getId()).stream().map(AppointmentHelper::convertEntityToDtoForAppointment).toList();
        ;
        calendarDto.setAppointments(appointments);
        return calendarDto;
    }
}
