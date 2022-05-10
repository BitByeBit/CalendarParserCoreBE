package com.bitbybit.corebe.models;

import javax.persistence.*;

@Entity
@Table(name = "events")
@SequenceGenerator(name = "eventGenerator", sequenceName = "eventSequence")
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
    private String extra;

    public Event(String name, EventType type,
                 String timeslot, String weekday, Integer parity) {
        this.name = name;

        this.type = type;

        this.timeslot = timeslot;
        this.weekday = weekday;
        this.parity = parity;
    }

    public Long getEventid() {
        return eventid;
    }

    public String getName() {
        return name;
    }

    public EventType getType() {
        return type;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public String getWeekday() {
        return weekday;
    }

    public Integer getParity() {
        return parity;
    }

    public String getExtra() {
        return extra;
    }

    public void setParity(Integer parity) {
        this.parity = parity;
    }
}
