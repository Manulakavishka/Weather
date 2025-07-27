package com.example.weather.controller;

import com.example.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void testGetWeatherSummary() throws Exception {
        Map<String, Object> mockSummary = Map.of(
                "city", "London",
                "averageTemperature", 15.5,
                "hottestDay", "2024-11-20",
                "coldestDay", "2024-11-18"
        );

        Mockito.when(weatherService.getWeatherSummary("London"))
                .thenReturn(mockSummary);

        mockMvc.perform(get("/weather")
                        .param("city", "London")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("London"))
                .andExpect(jsonPath("$.averageTemperature").value(15.5))
                .andExpect(jsonPath("$.hottestDay").value("2024-11-20"))
                .andExpect(jsonPath("$.coldestDay").value("2024-11-18"));
    }
}
