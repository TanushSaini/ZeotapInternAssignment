package com.example.weather.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String apiKey = "986d26b2565877f68f036944128015ed";  // Replace with your OpenWeatherMap API key
    private final String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=";

    public String fetchWeatherData(String city) {
        String url = apiUrl + city + "&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
