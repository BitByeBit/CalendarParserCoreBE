package com.bitbybit.corebe.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "userGenerator", sequenceName = "userSequence")
public class User {
    @Id
    @GeneratedValue(generator = "userGenerator")
    @Column(name = "userid")
    private Long userid;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "serie")
    private String serie;

    @Column(name = "grupa")
    private String grupa;

    @Column(name = "semigrupa")
    private String semigrupa;

    @OneToOne
    @JoinColumn(name = "calendar")
    private Calendar calendar;

    public Long getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSerie() {
        return serie;
    }

    public String getGrupa() {
        return grupa;
    }

    public String getSemigrupa() {
        return semigrupa;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
