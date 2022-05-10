package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.repositories.CalendarRepository;
import com.bitbybit.corebe.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {
    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    public Calendar createCalendar(CalendarDto calendarDto) {
        Calendar calendar = new Calendar(calendarDto.year, calendarDto.series, calendarDto.semester,
                                        calendarDto.events, userService.getUser("username"));

        calendarRepository.save(calendar);

        calendar.getEvents().forEach(event -> {
            event.setCalendar(calendar);
            eventRepository.save(event);
        });

        return calendar;
    }

    public Calendar getCalendar(String username) {
        return calendarRepository.getCalendarByUser(username);
    }
}
