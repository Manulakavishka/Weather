package com.example.weather.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final WebClient webClient;

    private final String API_KEY = "add_your_api_key_here"; // your working key

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org").build();
    }

    public Map<String, Object> getWeatherSummary(String city) {
        Map<String, Object> response = new HashMap<>();

        // 1. Fetch forecast data from OpenWeather
        Map<String, Object> forecastData = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/data/2.5/forecast")
                        .queryParam("q", city)
                        .queryParam("appid", API_KEY)
                        .queryParam("units", "metric")
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (forecastData == null || forecastData.get("list") == null) {
            response.put("error", "Unable to fetch data");
            return response;
        }

        List<Map<String, Object>> forecastList = (List<Map<String, Object>>) forecastData.get("list");

        // 2. Group by day
        Map<String, List<Double>> dailyTemps = new HashMap<>();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Map<String, Object> item : forecastList) {
            String dateTime = (String) item.get("dt_txt");
            String date = LocalDate.parse(dateTime, inputFormatter).toString(); // just the date part
            Map<String, Object> main = (Map<String, Object>) item.get("main");
            Double temp = ((Number) main.get("temp")).doubleValue();

            dailyTemps.computeIfAbsent(date, k -> new ArrayList<>()).add(temp);
        }

        // 3. Compute daily averages
        Map<String, Double> dailyAvgTemps = new HashMap<>();
        for (String date : dailyTemps.keySet()) {
            List<Double> temps = dailyTemps.get(date);
            double avg = temps.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            dailyAvgTemps.put(date, avg);
        }

        // 4. Determine hottest and coldest day
        String hottestDay = Collections.max(dailyAvgTemps.entrySet(), Map.Entry.comparingByValue()).getKey();
        String coldestDay = Collections.min(dailyAvgTemps.entrySet(), Map.Entry.comparingByValue()).getKey();

        // 5. Overall average
        double overallAvg = dailyAvgTemps.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);

        // 6. Response
        response.put("city", forecastData.get("city") != null ? ((Map<String, Object>) forecastData.get("city")).get("name") : city);
        response.put("averageTemperature", Math.round(overallAvg * 10.0) / 10.0);
        response.put("hottestDay", hottestDay);
        response.put("coldestDay", coldestDay);

        return response;
    }
}
