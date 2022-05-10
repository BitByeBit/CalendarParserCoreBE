package com.bitbybit.corebe.dtos;

public class UserDataDto {
    private String series;
    private String group;
    private String semiGroup;

    public UserDataDto(String series, String group, String semiGroup) {
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
