package com.bitbybit.corebe.dtos;

import org.springframework.web.multipart.MultipartFile;

public class ParserDataDto {
    private UserDataDto user;
    private MultipartFile file;

    public ParserDataDto(UserDataDto user, MultipartFile file) {
        this.user = user;
        this.file = file;
    }

    public UserDataDto getUser() {
        return user;
    }

    public MultipartFile getFile() {
        return file;
    }
}
