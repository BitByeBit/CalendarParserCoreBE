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
    @Column(name = "eventid")
    @GeneratedValue(generator = "eventGenerator")
    private Long eventid;

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

    @ManyToOne
    @JoinColumn(name = "calendar")
    private Calendar calendar;

    public Event(String name, EventType type, String timeslot,
                 String weekday, Integer parity) {
        this.name = name;
        this.type = type;
        this.timeslot = timeslot;
        this.weekday = weekday;
        this.parity = parity;
    }

    public Event() {}
}
