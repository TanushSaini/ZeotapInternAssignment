package com.example.weather.dto;

import lombok.Data;

@Data
public class WeatherRequest {
    private String city;
    private String mainCondition;
    private double temperature;
}
