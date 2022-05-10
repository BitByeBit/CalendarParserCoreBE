package com.bitbybit.corebe.DTOS;

public class RegisterForm {
    private String username;
    private String password;
    private String matchingPassword;
    private String group;
    private String semiGroup;
    private String series;
    private int year;
    private int semester;

    public RegisterForm(String username, String  password, String matchingPassword, String group, String semiGroup, String series,
                        int year, int semester) {
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.group = group;
        this.semiGroup = semiGroup;
        this.series = series;
        this.year = year;
        this.semester = semester;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public String getGroup() {
        return group;
    }

    public String getSemiGroup() {
        return semiGroup;
    }

    public String getSeries() {
        return series;
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
    }
}
