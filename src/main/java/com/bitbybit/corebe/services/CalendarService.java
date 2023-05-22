package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.repositories.CalendarRepository;
import com.bitbybit.corebe.repositories.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import io.prometheus.client.Gauge;

@Service
public class CalendarService {
    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void createCalendar(CalendarDto calendarDto) {
        
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
    }

    public Calendar getCalendar(String userUid) {
        return calendarRepository.getCalendarByUserUid(userUid);
    }

    public void shareEvent(String srcUid, String dstUid, Long eventId) throws AccessDeniedException {
        Calendar srcCalendar = getCalendar(srcUid);

        Event srcEvent = srcCalendar.getEvents().stream()
                        .filter(event -> Objects.equals(event.getEventId(), eventId)).findFirst().orElse(null);

        if (Objects.isNull(srcEvent)) {
            throw new AccessDeniedException("You do not have access to this event!");
        }

        Calendar dstCalendar = getCalendar(dstUid);

        if (Objects.isNull(dstCalendar)) {
            throw new AccessDeniedException("User does not exist");
        }

        dstCalendar.addEvent(eventRepository.save(new Event(srcEvent.getName(),
                srcEvent.getType(), srcEvent.getTimeslot(), srcEvent.getWeekday(),
                srcEvent.getParity(), srcEvent.getExtra())));

        calendarRepository.save(dstCalendar);
    }

    public void saveCalendar(Calendar calendar) {
        this.calendarRepository.save(calendar);
    }
}
