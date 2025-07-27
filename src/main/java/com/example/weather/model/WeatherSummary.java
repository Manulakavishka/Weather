package com.example.weather.model;

public class WeatherSummary {
    private String city;
    private double averageTemperature;
    private String hottestDay;
    private String coldestDay;



    public WeatherSummary(String city, double avg, String hot, String cold) {
        this.city = city;
        this.averageTemperature = avg;
        this.hottestDay = hot;
        this.coldestDay = cold;
    }

    public WeatherSummary() {

    }

    // Getters and Setters

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public String getHottestDay() {
        return hottestDay;
    }

    public void setHottestDay(String hottestDay) {
        this.hottestDay = hottestDay;
    }

    public String getColdestDay() {
        return coldestDay;
    }

    public void setColdestDay(String coldestDay) {
        this.coldestDay = coldestDay;
    }
}

