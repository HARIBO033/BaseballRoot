package com.baseball_root.Weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadiumWeatherResponse {
    private String stadium;
    private double lat;
    private double lon;
    private List<WeatherForecast> forecast;
}