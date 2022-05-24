package com.bitbybit.corebe.controllers;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.dtos.EventDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.services.CalendarService;
import com.bitbybit.corebe.services.EventService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper mapper;



    @GetMapping("/getCalendar")
    public CalendarDto getCalendar() {
        Calendar calendar = calendarService.getCalendar("username");
        CalendarDto calendarDto = mapper.map(calendar, CalendarDto.class);
        return calendarDto;
    }

    @PostMapping("/addEvent")
    public EventDto addEvent(@RequestBody EventDto eventDto) {
        Event event = this.eventService.addEvent(eventDto);
        EventDto returnDto = mapper.map(event, EventDto.class);
        return returnDto;
    }

    @PostMapping("/editEvent")
    public EventDto editEvent(@RequestBody EventDto eventDto) {
        Event event = eventService.editEvent(eventDto);
        EventDto returnDto = mapper.map(event, EventDto.class);
        return returnDto;
    }

    @DeleteMapping("/deleteEvent")
    public void deleteEvent(@RequestBody Long eventId) {
        this.eventService.deleteEvent(eventId);
    }

    @GetMapping("/filter")
    public CalendarDto filterEvents(@RequestBody String tag) {
        Calendar calendar = calendarService.getCalendar("username");
        CalendarDto calendarDto = mapper.map(calendar, CalendarDto.class);
        calendarDto.events = calendarDto.events.stream().filter(eventDto -> eventDto.tag.equals(tag)).toList();
        return calendarDto;
    }
}
