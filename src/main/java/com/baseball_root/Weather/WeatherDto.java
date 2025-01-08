package com.baseball_root.Weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {
    private String location;//지역
    private String hour;//시간
    private String weather;//날씨
    private String temperature;//기온
    private String windChillTemperature;//체감온도
    private String precipitation;//강수량
    private String humidity;//습도


    public WeatherDto(String location, String weather, String temperature) {
        this.location = location;
        this.weather = weather;
        this.temperature = temperature;
    }

    public WeatherDto(String hour, String weather, String temperature, String windChillTemperature, String precipitation, String humidity) {
        this.hour = hour;
        this.weather = weather;
        this.temperature = temperature;
        this.windChillTemperature = windChillTemperature;
        this.precipitation = precipitation;
        this.humidity = humidity;
    }

    public static WeatherDto toLocationWeatherDto(String location, String weather, String temperature) {
        return new WeatherDto(location, weather, temperature);
    }

    public static WeatherDto toHourWeatherDto(String hour, String weather, String temperature, String windChillTemperature, String precipitation, String humidity) {
        return new WeatherDto(hour, weather, temperature, windChillTemperature, precipitation, humidity);
    }
}
