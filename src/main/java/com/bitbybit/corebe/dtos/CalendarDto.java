package com.bitbybit.corebe.dtos;

import com.bitbybit.corebe.models.Event;

import java.util.List;

public class CalendarDto {
    public String userUid;
    public List<EventDto> events;
    public String year;
    public String semester;
    public String series;
}
