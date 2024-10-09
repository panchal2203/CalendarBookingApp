package com.calendar.calendar.repository;

import com.calendar.calendar.entity.CalendarDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarDetail, Long> {

}
