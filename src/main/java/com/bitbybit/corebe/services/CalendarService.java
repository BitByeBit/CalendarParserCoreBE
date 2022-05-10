package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.repositories.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CalendarService {
    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private UserService userService;

    // save calendar
    // create calendar
    // get calendar
    // edit calendar

    public Calendar createCalendar(CalendarDto calendarDto) {
        Calendar calendar = new Calendar(calendarDto.year, calendarDto.series, calendarDto.semester,
                                        calendarDto.events, userService.getUser("username"));

        calendarRepository.save(calendar);
        return calendar;
    }
}
