package com.bitbybit.corebe.dtos;

import com.bitbybit.corebe.models.Calendar;
import com.bitbybit.corebe.models.EventType;

public class EventDto {
    public Long id;
    public String name;
    public EventType type;
    public String timeslot;
    public String weekday;
    public Integer parity;
    public String extra;
    public Calendar calendar;
}
