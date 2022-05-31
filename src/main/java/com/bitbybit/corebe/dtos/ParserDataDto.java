package com.bitbybit.corebe.dtos;

import com.bitbybit.corebe.models.User;
import org.springframework.web.multipart.MultipartFile;

public class ParserDataDto {
    public String userUid;
    public String series;
    public String group;
    public String subGroup;
    public MultipartFile file;
}
