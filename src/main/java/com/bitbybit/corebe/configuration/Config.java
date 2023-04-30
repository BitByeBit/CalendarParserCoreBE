package com.bitbybit.corebe.configuration;

import com.bitbybit.corebe.properties.ParserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ParserProperties.class, ParserProperties.class})
public class Config {
}
