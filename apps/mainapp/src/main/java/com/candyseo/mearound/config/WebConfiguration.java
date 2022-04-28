package com.candyseo.mearound.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@ComponentScan(basePackages = "com.candyseo.mearound")
@Configuration
public class WebConfiguration {


    @Profile("test")
    @Configuration
    public static class TestWebConfig {

    }
}
