package com.baseball_root.Weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecast {

    private String datetime;
    private int temperature;
    private String weather;
    private String icon;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WeatherForecastByThreeHour {
        private String datetime;
        private int temperature;//기온
        private int temperatureMin;//최저기온
        private int temperatureMax;//최고기온
        private int temperatureFeelsLike;//체감온도
        private String mainWeather;//날씨
        private String descriptionWeather;//날씨 설명
        private String icon;//아이콘
        private int humidity;//습도
        private int windSpeed;//풍속
    }
}
