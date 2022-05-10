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

    public User(String username, String password, String name,
                String serie, String grupa, String semigrupa,
                Calendar calendar) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.semigrupa = semigrupa;
        this.serie = serie;
        this.grupa = grupa;
        this.calendar = calendar;
    }

    public User() {}
}
