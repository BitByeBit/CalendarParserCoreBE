package com.bitbybit.corebe.DTOS;

public class UserData {
    private String series;
    private String group;
    private String semiGroup;

    public UserData(String series, String group, String semiGroup) {
            this.series = series;
            this.group = group;
            this.semiGroup = semiGroup;
    }

    public String getSeries() {
        return series;
    }

    public String getGroup() {
        return group;
    }

    public String getSemiGroup() {
        return semiGroup;
    }
}
