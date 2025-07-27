package com.example.weather.controller;

import com.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/weather")
class WeatherController {

    @Autowired
    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getWeatherSummary(@RequestParam String city) {
        Map<String, Object> summary = weatherService.getWeatherSummary(city);
        return ResponseEntity.ok(summary);
    }
}
