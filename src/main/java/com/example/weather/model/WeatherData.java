package com.example.weather.model;

public class WeatherData {
    private String date;
    private double temp;

    public WeatherData(String date, double temp) {
        this.date = date;
        this.temp = temp;
    }

    public String getDate() { return date; }
    public double getTemp() { return temp; }
}

