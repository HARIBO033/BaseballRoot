package com.baseball_root;

import com.baseball_root.crawler.WeatherCrawler;
import com.baseball_root.crawler.WeatherCrawler_v2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableJpaAuditing
public class BaseballRootApplication {
	public static void main(String[] args) throws IOException {

        SpringApplication.run(BaseballRootApplication.class, args);
        System.out.println("Hello, World!");

        // 날씨
		/*WeatherCrawler weatherCrawler = new WeatherCrawler();

		String[] v = new String[5];
		String s = weatherCrawler.get(58, 125, v);

		if (s == null)
		{ // ok!
			System.out.println("날짜 : " + v[0]);
			System.out.println("시간 : " + v[1]);
			System.out.println("날씨 : " + v[2]);
			System.out.println("기온 : " + v[3] + "℃");
			System.out.println("습도 : " + v[4] + "%");
		}
		else
		{ // error
			System.out.println("Error : " + s);
		}*/


        WeatherCrawler_v2 weatherCrawler_v2 = new WeatherCrawler_v2();
        /*Map<String, String> locationKeywords = new HashMap<>();
        locationKeywords.put("고척 스카이돔", "서울 구로구");
        locationKeywords.put("잠실운동장", "서울 송파구");
        locationKeywords.put("삼성 라이온즈 파크", "대구 수성구");
        locationKeywords.put("기아 챔피언스필드", "광주 북구 임동");
        locationKeywords.put("수원 KT 위즈파크", "경기 수원시");
        locationKeywords.put("창원 NC 파크", "경남 창원시");
        locationKeywords.put("인천 SSG 랜더스필드", "인천 미추홀구");
        locationKeywords.put("한화생명 이글스파크", "대전 중구 부사동");
        locationKeywords.put("부산 사직야구장", "부산 동래구 사직동");

        for (String location : locationKeywords.keySet()) {
            try {
                String keyword = locationKeywords.get(location);
                System.out.println("\n" + location + " 날씨 정보:");
                weatherCrawler_v2.fetchWeatherInfo(keyword);
            } catch (Exception e) {
                System.err.println("Error fetching weather for " + location + ": " + e.getMessage());
            }
        }*/
        weatherCrawler_v2.fetchWeatherInfo("서울 송파구");
	}
}
