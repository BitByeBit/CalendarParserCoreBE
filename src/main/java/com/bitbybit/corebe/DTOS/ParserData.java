package com.bitbybit.corebe.DTOS;

import org.springframework.web.multipart.MultipartFile;

public class ParserData {
    private UserData user;
    private MultipartFile file;

    public ParserData(UserData user, MultipartFile file) {
        this.user = user;
        this.file = file;
    }

    public UserData getUser() {
        return user;
    }

    public MultipartFile getFile() {
        return file;
    }
}
