package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.repositories.CalendarRepository;
import com.bitbybit.corebe.repositories.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class CalendarService {
    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Calendar createCalendar(CalendarDto calendarDto) {
        List<Event> events = calendarDto.events.stream().map(eventDto -> {
            Event event = modelMapper.map(eventDto, Event.class);
            eventRepository.save(event);
            return event;
        }).toList();
        Calendar calendar = new Calendar(calendarDto.year, calendarDto.series, calendarDto.semester,
                                        events, calendarDto.userUid);

        Calendar userCalendar = getCalendar(calendarDto.userUid);

        if (Objects.nonNull(userCalendar)) {
            calendarRepository.delete(userCalendar);
        }

        calendarRepository.save(calendar);

        eventRepository.saveAll(calendar.getEvents());

        return calendar;
    }

    public Calendar getCalendar(String userUid) {
        return calendarRepository.getCalendarByUserUid(userUid);
    }

    public Calendar saveCalendar(Calendar calendar) {
        return this.calendarRepository.save(calendar);
    }
}
