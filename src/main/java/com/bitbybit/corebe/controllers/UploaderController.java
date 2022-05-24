package com.bitbybit.corebe.controllers;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.dtos.ParserDataDto;
import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.models.User;
import com.bitbybit.corebe.services.CalendarService;
import com.bitbybit.corebe.services.ParserService;
import com.bitbybit.corebe.services.UploaderService;
import com.bitbybit.corebe.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
public class UploaderController {
    @Autowired
    private UserService userService;

    @Autowired
    private UploaderService uploaderService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/upload")
    public CalendarDto upload(@RequestBody MultipartFile file) throws IOException {
        User user = userService.getUser("username");
        uploaderService.uploadCalendar(user, file);
        return modelMapper.map(calendarService.getCalendar("username"), CalendarDto.class);
    }
}
