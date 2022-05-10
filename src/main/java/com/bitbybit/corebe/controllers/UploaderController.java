package com.bitbybit.corebe.controllers;

import com.bitbybit.corebe.dtos.ParserDataDto;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.services.ParserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class UploaderController {
    private ParserService parserService = new ParserService();
}
