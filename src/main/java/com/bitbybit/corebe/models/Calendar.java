package com.bitbybit.corebe.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "calendars")
@SequenceGenerator(name = "calendarGenerator", sequenceName = "calendarSequence")
@Getter
@Setter
public class Calendar {
    @Id
    @Column(name = "calendarId")
    @GeneratedValue(generator = "calendarGenerator")
    private Long calendarId;

    @Column(name = "year")
    private String year;

    @Column(name = "series")
    private String series;

    @Column(name = "semester")
    private String semester;

    @OneToMany
    @JoinColumn(name = "events")
    private List<Event> events;

    @OneToOne
    @JoinColumn(name = "users")
    private User user;

    public Calendar(String year, String series, String semester,
                    List<Event> events, User user) {
        this.year = year;
        this.series = series;
        this.semester = semester;
        this.events = events;
        this.user = user;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public Calendar() {}
}
