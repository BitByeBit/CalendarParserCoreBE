package com.bitbybit.corebe.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;

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

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "events")
    private List<Event> events;

    @Column(name = "userUid", unique = true)
    private String userUid;

    public Calendar(String year, String series, String semester,
                    List<Event> events, String userUid) {
        this.year = year;
        this.series = series;
        this.semester = semester;
        this.events = events;
        this.userUid = userUid;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public Calendar() {}
}
