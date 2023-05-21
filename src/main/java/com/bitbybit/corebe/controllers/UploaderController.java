package com.bitbybit.corebe.controllers;

import com.bitbybit.corebe.dtos.CalendarDto;
import com.bitbybit.corebe.services.AuthService;
import com.bitbybit.corebe.services.CalendarService;
import com.bitbybit.corebe.services.UploaderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploaderController {
    @Autowired
    private UploaderService uploaderService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/upload")
    public ResponseEntity<CalendarDto> upload(@RequestHeader("Authorization") String userToken,
                                             @RequestParam("user_uid") String userUid,
                                             @RequestParam("series") String series,
                                             @RequestParam("group") String group,
                                             @RequestParam("subgroup") String subGroup,
                                             @RequestBody MultipartFile file) throws IOException {
        if (userUid == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if (userToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (authService.isInvalidUser(userUid, userToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if (file == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        uploaderService.uploadCalendar(userUid, series, group, subGroup, file);
        return ResponseEntity.ok(modelMapper.map(calendarService.getCalendar(userUid), CalendarDto.class));
    }
}
