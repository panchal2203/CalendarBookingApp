package com.calendar.calendar.dto;

import com.calendar.calendar.constant.ConfigConstant;
import com.calendar.calendar.utility.Utility;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class CalendarDto {
    private Long id;
    private String heading;
    private String description;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalTime startSlot;
    private LocalTime endSlot;
    private Integer slotDurationInMin;
    private Long calendarOwnerId;

    private String timeZone;

    private List<AppointmentDto> appointments;

    public void updateTimeObjectsToBaseZone() {
        if (timeZone!= null && !timeZone.equals(ConfigConstant.BASE_TIME_ZONE)) {
            this.startTime = Utility.convertToBaseZone(startTime, timeZone);
            this.endTime = Utility.convertToBaseZone(startTime, timeZone);
        }
    }
    public void updateTimeObjectsToUserZone() {
        if (timeZone!= null && !timeZone.equals(ConfigConstant.BASE_TIME_ZONE)) {
            this.startTime = Utility.convertToUserZone(startTime, timeZone);
            this.endTime = Utility.convertToUserZone(startTime, timeZone);
        }
    }


}
