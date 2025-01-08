package com.baseball_root.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration("globalWebConfig")
@ComponentScan(
        basePackages = "com.baseball_root",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.baseball_root.global.config.WebConfig.class)
)
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ByteArrayHttpMessageConverter());
    }
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

    @Bean(name = "globalCorsConfigurer")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 모든 경로에 대해 적용
                        .allowedOrigins("http://15.165.85.56:9090")  // 허용할 도메인
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드
                        .allowedHeaders("*")  // 허용할 헤더
                        .allowCredentials(true);  // 쿠키 포함 여부
            }
        };
    }


}
