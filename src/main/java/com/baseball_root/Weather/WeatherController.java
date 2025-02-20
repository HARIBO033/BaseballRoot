package com.baseball_root.Weather;

import com.baseball_root.crawler.WeatherCrawler;
import com.baseball_root.global.response.CommonResponse;
import com.baseball_root.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherController {
    private final WeatherCrawler weatherCrawler;

    //keyword는 null 일 수 있음
    @GetMapping("/weather/weatherDetail/{keyword}")
    public CommonResponse<List<WeatherDto>> getWeatherDetailInfo(@PathVariable(name = "keyword") String keyword) throws IOException {
        List<WeatherDto> weatherDtoList = weatherCrawler.getWeatherDetail(keyword);
        log.info("getWeatherDetailInfo 호출 weatherDtoList = " + weatherDtoList);
        return CommonResponse.success(SuccessCode.REQUEST_SUCCESS, weatherDtoList);
    }

    @GetMapping("/weather/weatherByLocation")
    public CommonResponse<List<WeatherByLocationDto>> getLocationWeatherInfo() throws IOException {
        List<WeatherByLocationDto> LocationWeatherDtoList = weatherCrawler.getLocationWeather();
        log.info("getLocationWeatherInfo 호출 LocationWeatherDtoList = " + LocationWeatherDtoList);
        return CommonResponse.success(SuccessCode.REQUEST_SUCCESS, LocationWeatherDtoList);
    }

}
