package com.baseball_root.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

        /*@Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins("http://localhost:3000")
                            .allowedMethods("GET", "POST", "PUT", "DELETE");
                }
            };
        }*/

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 모든 경로에 대해 적용
                        .allowedOrigins("http://15.165.85.56:9091")  // 허용할 도메인
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드
                        .allowedHeaders("*")  // 허용할 헤더
                        .allowCredentials(true);  // 쿠키 포함 여부
            }
        };
    }


}
