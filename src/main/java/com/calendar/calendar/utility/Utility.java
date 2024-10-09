package com.calendar.calendar.utility;

import com.calendar.calendar.constant.ConfigConstant;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utility {


    private static final ZoneId BASE_TIME_ZONE_ID = ZoneId.of(ConfigConstant.BASE_TIME_ZONE);

    public static LocalDateTime convertToBaseZone(LocalDateTime time, String timeZoneValue) {
        ZoneId userTimeZone = ZoneId.of(timeZoneValue);
        ZonedDateTime userTime = time.atZone(userTimeZone);
        ZonedDateTime baseTime = userTime.withZoneSameInstant(BASE_TIME_ZONE_ID);
        return baseTime.toLocalDateTime();
    }

    public static LocalDateTime convertToUserZone(LocalDateTime time, String timeZoneValue) {
        ZoneId userTimeZone = ZoneId.of(timeZoneValue);
        ZonedDateTime baseTime = time.atZone(BASE_TIME_ZONE_ID);
        ZonedDateTime userTime = baseTime.withZoneSameInstant(userTimeZone);
        return userTime.toLocalDateTime();
    }


}
