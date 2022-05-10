package com.bitbybit.corebe.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "calendars")
@SequenceGenerator(name = "calendarGenerator", sequenceName = "calendarSequence")
public class Calendar {
    @Id
    @Column(name = "calendarid")
    @GeneratedValue(generator = "calendarGenerator")
    private Long calendarid;

    @Column(name = "year")
    private Integer year;

    @Column(name = "serie")
    private String serie;

    @Column(name = "semester")
    private Integer semester;

    @OneToMany
    @JoinColumn(name = "events")
    private List<Event> events;

    public Long getCalendarid() {
        return calendarid;
    }

    public Integer getYear() {
        return year;
    }

    public String getSerie() {
        return serie;
    }

    public Integer getSemester() {
        return semester;
    }

    public List<Event> getEvents() {
        return events;
    }
}
