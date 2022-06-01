package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.dtos.ParserDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UploaderService {
    @Autowired
    private ParserService parserService;

    @Autowired
    private CalendarService calendarService;

    public void uploadCalendar(ParserDataDto parserDataDto) throws IOException {
        CalendarDto calendarDto = parserService.execParser(parserDataDto);
        calendarService.createCalendar(calendarDto);
    }
}
