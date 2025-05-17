package com.baseball_root.Weather;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weatherperson.api.key}")
    private String apiKey;

    @Value("${weatherperson.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = 3 * 60 * 60 * 1000) // 3시간 마다 실행
    public void clearWeatherCache() {
        cacheManager.getCache("stadiumWeather").clear();
        cacheManager.getCache("weatherForecast").clear();
        System.out.println("🧹 캐시 초기화됨 (3시간 주기)");
    }

    @Cacheable(value = "weatherForecast", key = "#p0", unless = "#result == null")//unless : 결과가 null이면 캐싱하지않음
    public WeatherResponse getWeatherForecast(String stadiumName) {
        System.out.println("cache check : getWeatherForecast");
        double[] coords = StadiumLocation.getCoordinates(stadiumName);
        if (coords == null) {
            throw new IllegalArgumentException("알 수 없는 야구장입니다.");
        }

        String url = String.format("%s?lat=%f&lon=%f&units=metric&lang=kr&appid=%s",
                apiUrl, coords[0], coords[1], apiKey);

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode body = response.getBody();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        long minDiff = Long.MAX_VALUE;
        WeatherForecast.WeatherForecastByThreeHour currentWeather = null;
        List<WeatherForecast.WeatherForecastByThreeHour> forecastList = new ArrayList<>();
        for (JsonNode item : body.get("list")) {
            String time = item.get("dt_txt").asText();
            int temp = item.get("main").get("temp").asInt();
            int tempMin = item.get("main").get("temp_min").asInt();
            int tempMax = item.get("main").get("temp_max").asInt();
            int feelLikeTemp = item.get("main").get("feels_like").asInt();
            String mainWeather = item.get("weather").get(0).get("main").asText();
            String descriptionWeather = item.get("weather").get(0).get("description").asText();
            String icon = item.get("weather").get(0).get("icon").asText();
            int humidity = item.get("main").get("humidity").asInt();
            int windSpeed = item.get("wind").get("speed").asInt();

            WeatherForecast.WeatherForecastByThreeHour forecast = new WeatherForecast.WeatherForecastByThreeHour(
                    time, temp, tempMin, tempMax, feelLikeTemp, mainWeather, descriptionWeather, icon, humidity, windSpeed
            );

            forecastList.add(forecast);

            LocalDateTime forecastTime = LocalDateTime.parse(time, formatter);
            long diff = Math.abs(Duration.between(now, forecastTime).toMinutes());

            if (diff < minDiff) {
                minDiff = diff;
                currentWeather = forecast;
            }
        }

        return new WeatherResponse(stadiumName, coords[0], coords[1], currentWeather, forecastList);
    }

    @Cacheable(value = "stadiumWeather", unless = "#result == null")
    public List<StadiumWeatherResponse> getAllStadiumWeather() {
        System.out.println("cache check : getAllStadiumWeather");
        List<StadiumWeatherResponse> result = new ArrayList<>();

        for (Map.Entry<String, double[]> entry : StadiumLocation.getAllStadiums().entrySet()) {
            String stadium = entry.getKey();
            double[] coords = entry.getValue();

            try {
                String url = String.format("%s?lat=%f&lon=%f&units=metric&lang=kr&appid=%s",
                        apiUrl, coords[0], coords[1], apiKey);

                ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
                JsonNode body = response.getBody();

                List<WeatherForecast> forecastList = new ArrayList<>();

                //1회전만 돌게
                JsonNode firstItem = body.get("list").get(0);
                String time = firstItem.get("dt_txt").asText();
                int temp = firstItem.get("main").get("temp").asInt();
                String weather = firstItem.get("weather").get(0).get("description").asText();
                String icon = firstItem.get("weather").get(0).get("icon").asText();


                forecastList.add(new WeatherForecast(time, temp, weather, icon));

                result.add(new StadiumWeatherResponse(stadium, coords[0], coords[1], forecastList));
            } catch (Exception e) {
                System.out.println("날씨 조회 실패: " + stadium);
                e.printStackTrace();
            }
        }

        return result;
    }
}
