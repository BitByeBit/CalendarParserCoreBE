package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.dtos.ParserDataDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
