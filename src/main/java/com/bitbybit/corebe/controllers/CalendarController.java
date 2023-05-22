package com.bitbybit.corebe.controllers;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.dtos.EventDto;
import com.bitbybit.corebe.dtos.EventShareDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.services.AuthService;
import com.bitbybit.corebe.services.CalendarService;
import com.bitbybit.corebe.services.EventService;
import io.prometheus.client.Counter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ModelMapper mapper;

    static final Counter requests = Counter.build().name("Total request").help("Total requests").register();
    static final Counter calendarRequests = Counter.build().name("Calendar request").help("Calendar requests").register();
    static final Counter addEventRequests = Counter.build().name("Add event request").help("Add event requests").register();
    static final Counter editEventRequests = Counter.build().name("Edit event request").help("Edit event requests").register();
    static final Counter deleteEventRequests = Counter.build().name("delete event request").help("Delete event requests").register();
    static final Counter filterCalendarRequests = Counter.build().name("Filter calendar request").help("Filter Calendar requests").register();
    static final Counter shareEventRequests = Counter.build().name("Share event request").help("Share event requests").register();


    @GetMapping("/calendar")
    public ResponseEntity<CalendarDto> getCalendar(@RequestHeader("Authorization") String userToken,
                                                   @RequestParam("user_uid") String userUid) {
        calendarRequests.inc();
        requests.inc();
        if (userUid == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (userToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (authService.isInvalidUser(userUid, userToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Calendar calendar = calendarService.getCalendar(userUid);
        return ResponseEntity.ok(mapper.map(calendar, CalendarDto.class));
    }

    @PostMapping("/add-event")
    public ResponseEntity<EventDto> addEvent(@RequestHeader("Authorization") String userToken,
                                             @RequestParam("user_uid") String userUid, @RequestBody EventDto eventDto) {
        addEventRequests.inc();
        requests.inc();
        if (userUid == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (userToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (authService.isInvalidUser(userUid, userToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Event event = this.eventService.addEvent(eventDto, userUid);
        return ResponseEntity.ok(mapper.map(event, EventDto.class));
    }

    @PostMapping("/edit-event")
    public ResponseEntity<EventDto> editEvent(@RequestHeader("Authorization") String userToken,
                                              @RequestParam("user_uid") String userUid,
                                              @RequestBody EventDto eventDto) throws AccessDeniedException {
        editEventRequests.inc();
        requests.inc();
        if (userUid == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (userToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (authService.isInvalidUser(userUid, userToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Event event = eventService.editEvent(eventDto, userUid);
        return ResponseEntity.ok(mapper.map(event, EventDto.class));
    }

    @DeleteMapping("/delete-event")
    public ResponseEntity<?> deleteEvent(@RequestHeader("Authorization") String userToken, @RequestParam("user_uid") String userUid,
                            @RequestBody Long eventId) throws AccessDeniedException {
        deleteEventRequests.inc();
        requests.inc();
        if (userUid == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (userToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (authService.isInvalidUser(userUid, userToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        this.eventService.deleteEvent(eventId, userUid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter-calendar")
    public ResponseEntity<CalendarDto> filterCalendar(@RequestHeader("Authorization") String userToken,
                                                      @RequestParam("user_uid") String userUid, @RequestParam("tag") String tag) {
        filterCalendarRequests.inc();
        requests.inc();
        if (userUid == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (userToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (authService.isInvalidUser(userUid, userToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Calendar calendar = calendarService.getCalendar(userUid);
        CalendarDto calendarDto = mapper.map(calendar, CalendarDto.class);
        calendarDto.events = calendarDto.events.stream().filter(eventDto -> eventDto.tag.equals(tag)).toList();
        return ResponseEntity.ok(calendarDto);
    }

    @PostMapping("/share-event")
    public ResponseEntity<?> shareEvent(@RequestHeader("Authorization") String userToken,
                           @RequestBody EventShareDto eventShareDto) throws AccessDeniedException {
        shareEventRequests.inc();
        requests.inc();
        if (eventShareDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (userToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (authService.isInvalidUser(eventShareDto.srcUid, userToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        calendarService.shareEvent(eventShareDto.srcUid, eventShareDto.dstUid, eventShareDto.eventId);
        return ResponseEntity.ok().build();
    }
}
