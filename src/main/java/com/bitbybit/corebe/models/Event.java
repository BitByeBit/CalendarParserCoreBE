package com.bitbybit.corebe.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "events")
@SequenceGenerator(name = "eventGenerator", sequenceName = "eventSequence")
@Getter
@Setter
public class Event {
    @Id
    @Column(name = "eventId")
    @GeneratedValue(generator = "eventGenerator")
    private Long eventId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private EventType type;

    @Column(name = "timeslot")
    private String timeslot;

    @Column(name = "weekday")
    private String weekday;

    @Column(name = "parity")
    private Integer parity;

    @Column(name = "extra")
    private String extra = "";

    @Column(name = "tag")
    private String tag = "";

    public Event(String name, EventType type, String timeslot,
                 String weekday, Integer parity, String extra) {
        this.name = name;
        this.type = type;
        this.timeslot = timeslot;
        this.weekday = weekday;
        this.parity = parity;
        this.extra = extra;
    }

    public Event(String name, EventType type, String timeslot,
                 String weekday, Integer parity, String extra, String tag) {
        this.name = name;
        this.type = type;
        this.timeslot = timeslot;
        this.weekday = weekday;
        this.parity = parity;
        this.extra = extra;
        this.tag = tag;
    }

    public Event() {}
}
