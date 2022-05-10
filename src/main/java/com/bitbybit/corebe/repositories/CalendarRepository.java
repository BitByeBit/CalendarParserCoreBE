package com.bitbybit.corebe.repositories;

import com.bitbybit.corebe.models.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
