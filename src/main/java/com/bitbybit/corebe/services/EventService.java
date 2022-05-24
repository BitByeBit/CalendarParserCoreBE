package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.EventDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.repositories.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private ModelMapper modelMapper;

    public Event save(Event event) {
        return this.eventRepository.save(event);
    }

    public Event addEvent(EventDto eventDto) {
        Event event = new Event(eventDto.name, eventDto.type, eventDto.timeslot, eventDto.weekday,
                eventDto.parity);
        event.setExtra(Objects.requireNonNullElse(eventDto.extra, ""));
        event = this.save(event);
        Calendar calendar = this.calendarService.getCalendar("username");
        calendar.addEvent(event);
        calendarService.saveCalendar(calendar);
        return event;
    }

    public Event editEvent(EventDto eventDto) {
        Event event = this.eventRepository.getById(eventDto.eventId);
        modelMapper.map(eventDto, event);
        return eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) {
        Event event = this.eventRepository.getById(eventId);
        this.eventRepository.delete(event);
    }

}
