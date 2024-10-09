package com.calendar.calendar.repository;

import com.calendar.calendar.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCalendarOwnerId(Long calendarOwnerId);

    List<Appointment> findByCalendarId(Long calendarId);

    @Query("SELECT a.startTime FROM Appointment a WHERE a.calendarOwnerId = :calendarOwnerId AND a.startTime BETWEEN :startTime AND :endTime")
    Set<LocalDateTime> getAppointmentsOfCalendarOwner(@Param("calendarOwnerId") Long calendarOwnerId,
                                                              @Param("startTime") LocalDateTime startTime,
                                                              @Param("endTime") LocalDateTime endTime);

}
