package com.bitbybit.corebe.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("parser")
public class ParserProperties {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
