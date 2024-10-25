package com.example.weather.controller;

import com.example.weather.model.DailyWeatherSummary;
import com.example.weather.services.AlertService;
import com.example.weather.services.WeatherProcessingService;
import com.example.weather.services.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherProcessingService weatherProcessingService;
    private final AlertService alertService;

    public WeatherController(WeatherService weatherService, WeatherProcessingService weatherProcessingService, AlertService alertService) {
        this.weatherService = weatherService;
        this.weatherProcessingService = weatherProcessingService;
        this.alertService = alertService;
    }

    @GetMapping("/weather/fetch")
    public String fetchWeatherData(@RequestParam String city) {
        return weatherService.fetchWeatherData(city);
    }

    @GetMapping("/weather/summaries")
    public List<DailyWeatherSummary> getDailySummaries() {
        return weatherProcessingService.getDailySummaries();
    }

    @GetMapping("/weather/check-alert")
    public String checkAlert(@RequestParam double temperature) {
        return alertService.checkAlert(temperature);
    }
}
