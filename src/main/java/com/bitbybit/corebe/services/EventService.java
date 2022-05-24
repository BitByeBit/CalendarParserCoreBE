package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.EventDto;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void save(Event event) {
        this.eventRepository.save(event);
    }

    public String addEvent(EventDto eventDto) {
        Event event = new Event(eventDto.name, eventDto.type, eventDto.timeslot, eventDto.weekday,
                eventDto.parity);
        event.setCalendar(eventDto.calendar);
        event.setExtra(eventDto.extra);
        this.save(event);
        return "Event added";
    }

    public String editEvent(EventDto eventDto) {
        Event event = this.eventRepository.getById(eventDto.id);
        if (eventDto.name != null) {
            event.setName(eventDto.name);
        }
        if (eventDto.type != null) {
            event.setType(eventDto.type);
        }
        if (eventDto.timeslot != null) {
            event.setTimeslot(eventDto.timeslot);
        }
        if (eventDto.weekday != null) {
            event.setWeekday(eventDto.weekday);
        }
        if (eventDto.parity != null) {
            event.setParity(eventDto.parity);
        }
        if (eventDto.extra != null) {
            event.setExtra(eventDto.extra);
        }
        this.eventRepository.save(event);


        return "Event edited";
    }

    public String deleteEvent(EventDto eventDto) {
        Event event = this.eventRepository.getById(eventDto.id);
        this.eventRepository.delete(event);

        return "Event deleted";
    }

}
