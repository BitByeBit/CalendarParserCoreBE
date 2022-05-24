package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.dtos.ParserDataDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class UploaderService {
    @Autowired
    private ParserService parserService;

    @Autowired
    private UserService userService;

    @Autowired
    private CalendarService calendarService;

    public void uploadCalendar(User user, MultipartFile file) throws IOException {
        ParserDataDto parserDataDto = new ParserDataDto();
        parserDataDto.user = user;
        parserDataDto.file = file;

        if (file == null) {
            return;
        }

        CalendarDto calendarDto = parserService.execParser(parserDataDto);

        user.setCalendar(calendarService.createCalendar(calendarDto));
        userService.save(user);
    }


}
