package com.bitbybit.corebe.controllers;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.dtos.ParserDataDto;
import com.bitbybit.corebe.services.CalendarService;
import com.bitbybit.corebe.services.UploaderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploaderController {
    @Autowired
    private UploaderService uploaderService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/uploadCalendar")
    public CalendarDto upload(@RequestParam("user_uid") String userUid,
                              @RequestParam("series") String series,
                              @RequestParam("group") String group,
                              @RequestParam("subgroup") String subGroup,
                              @RequestBody MultipartFile file) throws IOException {
        if (file == null) {
            throw new NullPointerException();
        }

        ParserDataDto parserDataDto = new ParserDataDto();
        parserDataDto.series = series;
        parserDataDto.group = group;
        parserDataDto.subGroup = subGroup;
        parserDataDto.file = file;

        parserDataDto.userUid = userUid;



        uploaderService.uploadCalendar(parserDataDto);
        return modelMapper.map(calendarService.getCalendar(userUid), CalendarDto.class);
    }
}
