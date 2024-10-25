package com.example.weather.services;

import com.example.weather.model.DailyWeatherSummary;
import com.example.weather.model.WeatherData;
import com.example.weather.repository.DailyWeatherSummaryRepository;
import com.example.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherProcessingService {

    private final WeatherRepository weatherRepository;
    private final DailyWeatherSummaryRepository summaryRepository;

    public WeatherProcessingService(WeatherRepository weatherRepository, DailyWeatherSummaryRepository summaryRepository) {
        this.weatherRepository = weatherRepository;
        this.summaryRepository = summaryRepository;
    }

    /**
     * Processes and saves the fetched weather data, converting temperature from Kelvin to Celsius.
     *
     * @param city City name for which data is being processed
     * @param mainCondition Main weather condition (e.g., Rain, Clear)
     * @param temperature Temperature in Kelvin to be converted to Celsius
     * @param feelsLike Feels-like temperature in Kelvin to be converted to Celsius
     */
    public void saveWeatherData(String city, String mainCondition, double temperature, double feelsLike) {
        WeatherData data = new WeatherData();
        data.setCity(city);
        data.setMainCondition(mainCondition);
        data.setTemperature(convertKelvinToCelsius(temperature));
        data.setFeelsLike(convertKelvinToCelsius(feelsLike));
        data.setTimestamp(LocalDateTime.now());

        weatherRepository.save(data);
    }

    /**
     * Retrieves all daily summaries.
     *
     * @return List of daily weather summaries.
     */
    public List<DailyWeatherSummary> getDailySummaries() {
        return summaryRepository.findAll();
    }

    /**
     * Calculates and saves a daily weather summary for a specified city.
     *
     * @param city City name for which the daily summary is calculated
     */
    public void calculateDailySummary(String city) {
        List<WeatherData> data = weatherRepository.findAll().stream()
                .filter(d -> d.getCity().equals(city) && d.getTimestamp().toLocalDate().equals(LocalDate.now()))
                .collect(Collectors.toList());

        if (data.isEmpty()) return;

        double avgTemp = data.stream().mapToDouble(WeatherData::getTemperature).average().orElse(0.0);
        double maxTemp = data.stream().mapToDouble(WeatherData::getTemperature).max().orElse(0.0);
        double minTemp = data.stream().mapToDouble(WeatherData::getTemperature).min().orElse(0.0);

        String dominantCondition = calculateDominantCondition(data);

        DailyWeatherSummary summary = new DailyWeatherSummary();
        summary.setCity(city);
        summary.setAvgTemperature(avgTemp);
        summary.setMaxTemperature(maxTemp);
        summary.setMinTemperature(minTemp);
        summary.setDominantCondition(dominantCondition);

        summaryRepository.save(summary);
    }

    /**
     * Converts a temperature from Kelvin to Celsius.
     *
     * @param kelvin Temperature in Kelvin
     * @return Temperature in Celsius
     */
    private double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    /**
     * Calculates the dominant weather condition based on the most frequently occurring condition.
     *
     * @param data List of WeatherData records
     * @return Dominant weather condition
     */
    private String calculateDominantCondition(List<WeatherData> data) {
        Map<String, Long> conditionCount = data.stream()
                .collect(Collectors.groupingBy(WeatherData::getMainCondition, Collectors.counting()));

        return conditionCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");
    }
}
