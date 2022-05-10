package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.repositories.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {
    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private UserService userService;

    // save calendar
    // create calendar
    // get calendar
    // edit calendar

    public void createCalendar(CalendarDto calendarDto) {
        Calendar calendar = new Calendar(calendarDto.year, calendarDto.series, calendarDto.semester,
                                        calendarDto.events, userService.getUser("username"));

        calendarRepository.save(calendar);
    }

    public Calendar getCalendar(String username) {
        return calendarRepository.getCalendarByUser(username);
    }
}
