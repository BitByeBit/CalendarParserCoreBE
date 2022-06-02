package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.EventDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.repositories.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;
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

    public Event addEvent(EventDto eventDto, String userUid) {
        Event event = new Event(eventDto.name, eventDto.type, eventDto.timeslot, eventDto.weekday,
                eventDto.parity, eventDto.extra, eventDto.tag);
        event.setExtra(Objects.requireNonNullElse(eventDto.extra, ""));
        event.setTag(Objects.requireNonNullElse(eventDto.tag, ""));
        event = this.save(event);

        Calendar calendar = this.calendarService.getCalendar(userUid);
        calendar.addEvent(event);
        calendarService.saveCalendar(calendar);
        return event;
    }

    public Event editEvent(EventDto eventDto, String userUid) throws AccessDeniedException {
        Calendar calendar = this.calendarService.getCalendar(userUid);
        Event event = this.eventRepository.getById(eventDto.eventId);

        if (!calendar.getEvents().contains(event)) {
            throw new AccessDeniedException("You do not have access to this event!");
        }

        modelMapper.map(eventDto, event);
        return eventRepository.save(event);
    }

    public void deleteEvent(Long eventId, String userUid) throws AccessDeniedException {
        Calendar calendar = this.calendarService.getCalendar(userUid);
        Event event = this.eventRepository.getById(eventId);

        if (!calendar.getEvents().contains(event)) {
            throw new AccessDeniedException("You do not have access to this event!");
        }

        this.eventRepository.delete(event);
    }
}
