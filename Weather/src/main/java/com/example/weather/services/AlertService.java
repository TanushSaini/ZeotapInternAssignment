package com.example.weather.services;

import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private static final double TEMP_THRESHOLD = 35.0;

    public String checkAlert(double temperature) {
        if (temperature > TEMP_THRESHOLD) {
            return "Alert: Temperature exceeded threshold!";
        }
        return "Temperature is within normal range.";
    }
}
