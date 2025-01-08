package com.baseball_root.Weather;

import com.baseball_root.crawler.WeatherCrawler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherCrawler weatherCrawler;

    //keyword는 null 일 수 있음
    @GetMapping("/weatherDetail/{keyword}")
    public ResponseEntity<List<WeatherDto>> getWeatherDetailInfo(@PathVariable(name = "keyword") String keyword) throws IOException {
        List<WeatherDto> weatherDtoList = weatherCrawler.getWeatherDetail(keyword);
        return ResponseEntity.ok(weatherDtoList);
    }

    @GetMapping("/weatherByLocation")
    public ResponseEntity<List<WeatherByLocationDto>> getLocationWeatherInfo() throws IOException {
        List<WeatherByLocationDto> LocationWeatherDtoList = weatherCrawler.getLocationWeather();
        return ResponseEntity.ok(LocationWeatherDtoList);
    }

}
