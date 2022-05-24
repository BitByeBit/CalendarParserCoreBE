package com.bitbybit.corebe.controllers;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.dtos.EventDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.services.CalendarService;
import com.bitbybit.corebe.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    @Autowired
    private EventService eventService;

    @GetMapping("/getCalendar")
    public CalendarDto getCalendar() {
        Calendar calendar = calendarService.getCalendar("username");
        CalendarDto calendarDto = new CalendarDto();
        calendarDto.events = calendar.getEvents();
        calendarDto.year = calendar.getYear();
        calendarDto.series = calendar.getSerie();
        calendarDto.semester = calendar.getSemester();

        return calendarDto;
    }

    @PostMapping("/addEvent")
    public String addEvent(@RequestBody EventDto eventDto) {
        eventDto.calendar = this.calendarService.getCalendar("username");
        return this.eventService.addEvent(eventDto);
    }

    @PostMapping("/editEvent")
    public String editEvent(@RequestBody EventDto eventDto) {
        return this.eventService.editEvent(eventDto);
    }
}
