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

    @OneToOne
    @JoinColumn(name = "user")
    private User user;

    public Calendar(Integer year, String serie, Integer semester,
                    List<Event> events, User user) {
        this.year = year;
        this.serie = serie;
        this.semester = semester;
        this.events = events;
        this.user = user;
    }

    public Calendar() {}
}
