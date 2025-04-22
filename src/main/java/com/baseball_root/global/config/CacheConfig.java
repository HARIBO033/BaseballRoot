package com.baseball_root.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;

@Configuration
public class CacheConfig {



    @Bean
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager(
                "weather", "stadiumWeather", "weatherForecast", "stadiumWeatherForecast");
    }
}
