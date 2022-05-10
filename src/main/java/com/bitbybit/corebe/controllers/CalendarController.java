package com.bitbybit.corebe.controllers;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

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
}
