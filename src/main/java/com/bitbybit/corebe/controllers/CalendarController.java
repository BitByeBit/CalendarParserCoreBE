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

import java.nio.file.AccessDeniedException;

@RestController
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/getCalendar")
    public CalendarDto getCalendar(@RequestParam("user_uid") String userUid) {
        Calendar calendar = calendarService.getCalendar(userUid);
        CalendarDto calendarDto = mapper.map(calendar, CalendarDto.class);
        return calendarDto;
    }

    @PostMapping("/addEvent")
    public EventDto addEvent(@RequestParam("user_uid") String userUid, @RequestBody EventDto eventDto) {
        Event event = this.eventService.addEvent(eventDto, userUid);
        EventDto returnDto = mapper.map(event, EventDto.class);
        return returnDto;
    }

    @PostMapping("/editEvent")
    public EventDto editEvent(@RequestParam("user_uid") String userUid,
                              @RequestBody EventDto eventDto) throws AccessDeniedException {
        Event event = eventService.editEvent(eventDto, userUid);
        EventDto returnDto = mapper.map(event, EventDto.class);
        return returnDto;
    }

    @DeleteMapping("/deleteEvent")
    public void deleteEvent(@RequestParam("user_uid") String userUid,
                            @RequestBody Long eventId) throws AccessDeniedException {
        this.eventService.deleteEvent(eventId, userUid);
    }

    @GetMapping("/filterCalendar")
    public CalendarDto filterEvents(@RequestParam("user_uid") String userUid, @RequestParam("tag") String tag) {
        Calendar calendar = calendarService.getCalendar(userUid);
        CalendarDto calendarDto = mapper.map(calendar, CalendarDto.class);
        calendarDto.events = calendarDto.events.stream().filter(eventDto -> eventDto.tag.equals(tag)).toList();
        return calendarDto;
    }
}
