package com.bitbybit.corebe.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "userGenerator", sequenceName = "userSequence")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(generator = "userGenerator")
    @Column(name = "userId")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "series")
    private String series;

    @Column(name = "[group]")
    private String group;

    @Column(name = "subGroup")
    private String subGroup;

    @OneToOne
    @JoinColumn(name = "calendar")
    private Calendar calendar;

    public User(String username, String password, String name,
                String series, String group, String subGroup,
                Calendar calendar) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.subGroup = subGroup;
        this.series = series;
        this.group = group;
        this.calendar = calendar;
    }

    public User() {}
}
