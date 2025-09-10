package com.baseball_root.global.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfig {

    @Bean
    public RedisConnectionFactory connectionFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        configuration.setPassword("fpeltm033!@");
        return new LettuceConnectionFactory(configuration);
    }

    //tODO: 캐시별로 TTL 다르게 설정하기
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        // JSON 직렬화 Serializer
        RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer();

        // 기본 캐시 설정
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))   // 기본 TTL 60분
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(serializer)
                )
                .disableCachingNullValues();        // null 값 캐싱 방지

        // 캐시별 TTL 설정
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("weather", defaultConfig.entryTtl(Duration.ofHours(1)));
        cacheConfigurations.put("stadiumWeather", defaultConfig.entryTtl(Duration.ofHours(1)));
        cacheConfigurations.put("weatherForecast", defaultConfig.entryTtl(Duration.ofHours(1)));
        cacheConfigurations.put("stadiumWeatherForecast", defaultConfig.entryTtl(Duration.ofHours(1)));
        cacheConfigurations.put("kboSchedule", defaultConfig.entryTtl(Duration.ofHours(12)));
        cacheConfigurations.put("issueCount", defaultConfig.entryTtl(Duration.ofSeconds(60)));

        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    /**
     * RedisTemplate - 직접 Redis 조작용 (조회수, 좋아요, 세션 등)
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Key는 String, Value는 JSON 직렬화
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Hash 구조에서 사용할 Serializer
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
