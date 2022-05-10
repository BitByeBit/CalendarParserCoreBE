package com.bitbybit.corebe.controllers;

import com.bitbybit.corebe.DTOS.LoginForm;
import com.bitbybit.corebe.DTOS.ParserData;
import com.bitbybit.corebe.DTOS.RegisterForm;
import com.bitbybit.corebe.services.Authenticator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AuthenticationController {
    public Authenticator auth = new Authenticator();
    public ParserData parserData;
    public AuthenticationController () {}

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterForm registerForm) {
        auth.register(registerForm);
        return null;
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginForm loginForm) {
        auth.login(loginForm);
        return null;
    }

    @PostMapping("/upload")
    public String upload(@RequestBody MultipartFile file) {
           parserData = new ParserData(auth.userData, file);
           return null;
    }
}
