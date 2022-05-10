package com.bitbybit.corebe.repositories;

import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    @Query(value = "SELECT c from Calendar c WHERE c.user.username = :username")
    Calendar getCalendarByUser(String username);
}
