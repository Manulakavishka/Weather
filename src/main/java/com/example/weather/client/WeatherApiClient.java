package com.example.weather.client;

import com.example.weather.model.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class WeatherApiClient {

//    @Value("${openweathermap.apiKey}")
    private String apiKey = "7c5312573d87826a182b4feaede5765e";

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openweathermap.org/data/2.5")
            .build();

    @Async
    public CompletableFuture<List<WeatherData>> fetchWeatherData(String city) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast")
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric")
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(error -> {
                            System.err.println("API error: " + error); // log error
                            return Mono.error(new RuntimeException("API Error: " + error));
                        }))
                .bodyToMono(JsonNode.class)
                .map(this::parseWeatherData)
                .toFuture();
    }

    private List<WeatherData> parseWeatherData(JsonNode node) {
        List<WeatherData> result = new ArrayList<>();
        Map<String, List<Double>> tempMap = new HashMap<>();

        for (JsonNode item : node.path("list")) {
            String date = item.path("dt_txt").asText().split(" ")[0];
            double temp = item.path("main").path("temp").asDouble();
            tempMap.computeIfAbsent(date, k -> new ArrayList<>()).add(temp);
        }

        for (Map.Entry<String, List<Double>> entry : tempMap.entrySet()) {
            double avg = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            result.add(new WeatherData(entry.getKey(), avg));
        }

        return result;
    }
}
