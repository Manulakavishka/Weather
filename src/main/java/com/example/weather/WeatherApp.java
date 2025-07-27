package com.example.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WeatherApp {
    public static void main(String[] args) {
        SpringApplication.run(WeatherApp.class, args);
    }
}
