package com.bitbybit.corebe.services;

import com.bitbybit.corebe.dtos.CalendarDto;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Service
public class UploaderService {
    @Value("${parser.url}")
    private String parserUrl;

    @Autowired
    private CalendarService calendarService;

    public void uploadCalendar(String userUid,
                               String series,
                               String group,
                               String subGroup,
                               MultipartFile file) throws IOException {
        WebClient webClient = WebClient.create(parserUrl);
        MultipartBodyBuilder fileBuilder = new MultipartBodyBuilder();
        fileBuilder.part("file", file.getResource());
        CalendarDto calendarDto = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/parse")
                        .queryParam("user_uid", userUid)
                        .queryParam("series", series)
                        .queryParam("group", group)
                        .queryParam("subgroup", subGroup)
                        .build())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(fileBuilder.build()))
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(CalendarDto.class);
                    } else {
                        throw new ServiceException("Parsing the calendar failed!");
                    }
                })
                .block();
        if (calendarDto == null) {
            throw new IOException("CalendarDto is null");
        }
        calendarService.createCalendar(calendarDto);
    }
}
