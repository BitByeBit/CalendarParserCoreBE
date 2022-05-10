package com.bitbybit.corebe.DAO;

import com.bitbybit.corebe.models.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarDAO extends JpaRepository<Calendar, Long> {
}
