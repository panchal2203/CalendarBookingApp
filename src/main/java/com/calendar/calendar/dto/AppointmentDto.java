package com.calendar.calendar.dto;

import com.calendar.calendar.constant.ConfigConstant;
import com.calendar.calendar.utility.Utility;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {

    private Long id;
    private Long calendarId;
    private Long calendarOwnerId;
    private String inviteeEmailAddress;
    private String inviteeFullName;
    private String notesByInvitee;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String meetingUrl;

    private String timeZone;

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
