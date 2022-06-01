package com.bitbybit.corebe.repositories;

import com.bitbybit.corebe.models.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    @Query(value = "SELECT c from Calendar c WHERE c.userUid = :userUid")
    Calendar getCalendarByUserUid(String userUid);
}
