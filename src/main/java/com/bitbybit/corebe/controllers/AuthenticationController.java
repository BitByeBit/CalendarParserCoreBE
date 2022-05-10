package com.bitbybit.corebe.controllers;

import com.bitbybit.corebe.dtos.LoginDto;
import com.bitbybit.corebe.dtos.ParserDataDto;
import com.bitbybit.corebe.dtos.RegisterDto;
import com.bitbybit.corebe.models.Event;
import com.bitbybit.corebe.services.AuthenticatorService;
import com.bitbybit.corebe.services.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
public class AuthenticationController {
    public AuthenticatorService auth = new AuthenticatorService();
//    public ParserDataDto parserData;

    @Autowired
    private ParserService parserService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterDto registerForm) {
        return auth.register(registerForm);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginDto loginForm) {
        auth.login(loginForm);
        return null;
    }

    @PostMapping("/upload")
    public List<Event> upload(@RequestBody MultipartFile file) throws IOException {
        ParserDataDto parserData = new ParserDataDto(auth.userData, file);

        if (file == null) {
            return Collections.emptyList();
        }

        return parserService.execParser(parserData);
    }
}
