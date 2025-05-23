package com.baseball_root.Weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {
    private String stadium;
    private double lat;
    private double lon;
    private WeatherForecast.WeatherForecastByThreeHour currentWeather;
    private List<WeatherForecast.WeatherForecastByThreeHour> forecast;
}

