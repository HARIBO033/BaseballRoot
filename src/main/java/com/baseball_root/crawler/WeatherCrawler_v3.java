package com.baseball_root.crawler;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.List;

public class WeatherCrawler_v3 {
    public static void fetchWeatherInfo(String keyword) throws IOException {
        //System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Headless 모드
        options.addArguments("--no-sandbox"); // 샌드박스 비활성화
        options.addArguments("--disable-dev-shm-usage"); // /dev/shm 사용 비활성화
        options.addArguments("--disable-gpu"); // GPU 사용 비활성화
        WebDriver driver = new ChromeDriver(options);

        String WeatherURL = "https://weather.naver.com/today";
        Document doc = Jsoup.connect(WeatherURL).get();
        //HTML로 부터 데이터 가져오기 Elements elem = doc.select(".weather_area .summary .weather");
        // 원하는 태그 선택 String[] str = elem.text().split(" ");
        // 정보 파싱 model.addAttribute("weather", elem); System.out.println(elem);


    }
}
