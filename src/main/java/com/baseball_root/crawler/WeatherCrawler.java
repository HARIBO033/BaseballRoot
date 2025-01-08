package com.baseball_root.crawler;

import com.baseball_root.Weather.WeatherByLocationDto;
import com.baseball_root.Weather.WeatherDto;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
//selenium sleep
import static java.lang.Thread.sleep;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

@Slf4j
@Service
public class WeatherCrawler {
    public List<WeatherDto> getWeatherDetail(String keyword) throws IOException {
        List<WeatherDto> weatherDtoList = new ArrayList<>();
        //System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriverManager.chromedriver().clearDriverCache();
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Headless 모드
        options.addArguments("--no-sandbox"); // 샌드박스 비활성화
        options.addArguments("--disable-dev-shm-usage"); // /dev/shm 사용 비활성화
        options.addArguments("--disable-gpu"); // GPU 사용 비활성화
        WebDriver driver = null;
        // 크롤링할 URL (기상청 또는 날씨 제공 사이트)
        Map<String, String> locationKeywords = new LinkedHashMap<>();
        locationKeywords.put("고척스카이돔", "https://www.weather.go.kr/w/index.do#dong/1153072000/37.4982338495579/126.867104761712/%EC%84%9C%EC%9A%B8%20%EA%B5%AC%EB%A1%9C%EA%B5%AC%20%EA%B3%A0%EC%B2%99%EB%8F%99/SCH/%EA%B3%A0%EC%B2%99%EC%8A%A4%EC%B9%B4%EC%9D%B4%EB%8F%94");
        locationKeywords.put("잠실종합운동장", "https://www.weather.go.kr/w/index.do#dong/1171072000/37.5145906150942/127.073024865801/%EC%84%9C%EC%9A%B8%20%EC%86%A1%ED%8C%8C%EA%B5%AC%20%EC%9E%A0%EC%8B%A4%EB%8F%99/SCH/%EC%9E%A0%EC%8B%A4%EC%A2%85%ED%95%A9%EC%9A%B4%EB%8F%99%EC%9E%A5");
        locationKeywords.put("삼성라이온즈파크", "https://www.weather.go.kr/w/index.do#dong/2726068000/35.8410595632468/128.681659448344/%EB%8C%80%EA%B5%AC%20%EC%88%98%EC%84%B1%EA%B5%AC%20%EC%97%B0%ED%98%B8%EB%8F%99/SCH/%EB%8C%80%EA%B5%AC%EC%82%BC%EC%84%B1%EB%9D%BC%EC%9D%B4%EC%98%A8%EC%A6%88%ED%8C%8C%ED%81%AC");
        locationKeywords.put("기아챔피언스필드", "https://www.weather.go.kr/w/index.do#dong/2917060200/35.16820922209541/126.88911206152956/%EA%B4%91%EC%A3%BC%20%EB%B6%81%EA%B5%AC%20%EC%9E%84%EB%8F%99/SCH/%EA%B4%91%EC%A3%BC%EA%B8%B0%EC%95%84%EC%B1%94%ED%94%BC%EC%96%B8%EC%8A%A4%ED%95%84%EB%93%9C");
        locationKeywords.put("수원KT위즈파크", "https://www.weather.go.kr/w/index.do#dong/4111159100/37.2997302532973/127.009772045935/%EA%B2%BD%EA%B8%B0%20%EC%88%98%EC%9B%90%EC%8B%9C%EC%9E%A5%EC%95%88%EA%B5%AC%20%EC%A1%B0%EC%9B%90%EB%8F%99/SCH/%EC%88%98%EC%9B%90KT%EC%9C%84%EC%A6%88%ED%8C%8C%ED%81%AC");
        locationKeywords.put("창원NC파크", "https://www.weather.go.kr/w/index.do#dong/4812757000/35.22280070751199/128.5820053292696/%EA%B2%BD%EB%82%A8%20%EC%B0%BD%EC%9B%90%EC%8B%9C%EB%A7%88%EC%82%B0%ED%9A%8C%EC%9B%90%EA%B5%AC%20%EC%96%91%EB%8D%95%EB%8F%99/SCH/%EC%B0%BD%EC%9B%90NC%ED%8C%8C%ED%81%AC");
        locationKeywords.put("인천SSG랜더스필드", "https://www.weather.go.kr/w/index.do#dong/2817771000/37.436998685442084/126.69327612453377/%EC%9D%B8%EC%B2%9C%20%EB%AF%B8%EC%B6%94%ED%99%80%EA%B5%AC%20%EB%AC%B8%ED%95%99%EB%8F%99/SCH/%EC%9D%B8%EC%B2%9CSSG%EB%9E%9C%EB%8D%94%EC%8A%A4%ED%95%84%EB%93%9C");
        locationKeywords.put("한화생명이글스파크", "https://www.weather.go.kr/w/index.do#dong/3014057500/36.31708044187382/127.42916818407977/%EB%8C%80%EC%A0%84%20%EC%A4%91%EA%B5%AC%20%EB%B6%80%EC%82%AC%EB%8F%99/SCH/%ED%95%9C%ED%99%94%EC%83%9D%EB%AA%85%EC%9D%B4%EA%B8%80%EC%8A%A4%ED%8C%8C%ED%81%AC");
        locationKeywords.put("부산사직야구장", "https://www.weather.go.kr/w/index.do#dong/2626058000/35.194017568250274/129.06154402103502/%EB%B6%80%EC%82%B0%20%EB%8F%99%EB%9E%98%EA%B5%AC%20%EC%82%AC%EC%A7%81%EB%8F%99/SCH/%EB%B6%80%EC%82%B0%EC%82%AC%EC%A7%81%EC%A2%85%ED%95%A9%EC%9A%B4%EB%8F%99%EC%9E%A5%20%EC%82%AC%EC%A7%81%EC%95%BC%EA%B5%AC%EC%9E%A5");

        if (keyword == null) {
            for (String value : locationKeywords.values()) {
                driver = new ChromeDriver(options);
                System.out.println("@@@@ value : " + value);

                driver.get(value);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                    WebElement clickATag = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("/html/body/div[3]/section/div/div[2]/div[5]/div[3]/div/div[3]/div[1]/div[1]/div[3]/div[1]/div/div/a[2]")
                    ));
                    clickATag.click();

                    List<WebElement> webElementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.xpath("/html/body/div[3]/section/div/div[2]/div[5]/div[3]/div/div[3]/div[1]/div[1]/div[5]/div[2]/div[1]/div[1]/div/div[2]/ul[1]")
                    ));

                    WebElement webElement = webElementList.get(0);
                    try {
                        // 요소를 다시 탐색하여 stale 방지
                        WebElement weatherElement = webElement.findElement(By.xpath("./li[2]/span[2]"));
                        WebElement temperatureElement = webElement.findElement(By.xpath("./li[3]/span[2]"));
                        String temperatureParts = temperatureElement.getAttribute("textContent").split("\\(")[0];

                        String key = locationKeywords.entrySet()
                                .stream()
                                .filter(entry -> entry.getValue().equals(value))
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .orElse(null);

                        weatherDtoList.add(
                                WeatherDto.toLocationWeatherDto(key, weatherElement.getText(), temperatureParts)
                        );

                    } catch (StaleElementReferenceException e) {
                        System.out.println("Stale Element Reference Exception, Retrying...");
                        continue; // 요소를 재탐색합니다.
                    }

                } catch (Exception e) {
                    log.error("Location Error: " + e.getMessage());
                } finally {
                    driver.quit();
                }
            }
            return weatherDtoList;
        } else if (keyword != null) {
            driver = new ChromeDriver(options);
            try {
                driver.get(locationKeywords.get(keyword));
                WebElement clickATag = driver.findElement(By.xpath("/html/body/div[3]/section/div/div[2]/div[5]/div[3]/div/div[3]/div[1]/div[1]/div[3]/div[1]/div/div/a[2]"));
                clickATag.click();
                //Thread.sleep(3000);

                driver.getPageSource();

                driver.switchTo().defaultContent();
                List<WebElement> webElementList = driver.findElements(By.xpath("/html/body/div[3]/section/div/div[2]/div[5]/div[3]/div/div[3]/div[1]/div[1]/div[5]/div[2]/div[1]/div[1]/div/div[2]/ul"));
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                System.out.println("INVOKE ELEMENTS@@@@@@@@@@@@@@@@@@");
                for (WebElement webElement : webElementList) {
                    WebElement timeElement = webElement.findElement(By.xpath("./li[1]/span[2]"));//시간
                    WebElement weatherElement = webElement.findElement(By.xpath("./li[2]/span[2]"));//날씨

                    WebElement temperatureElement = webElement.findElement(By.xpath("./li[3]/span[2]"));//기온
                    String allText = temperatureElement.getAttribute("textContent");
                    String temperatureParts = allText.split("\\(")[0];

                    WebElement feelingTemperatureElement = webElement.findElement(By.xpath("./li[4]/span[2]"));//체감온도
                    WebElement windElement = webElement.findElement(By.xpath("./li[8]/span[3]"));//바람
                    WebElement humidityElement = webElement.findElement(By.xpath("./li[9]/span[2]"));//습도

                    if (timeElement == null) {
                        break;
                    }

                    weatherDtoList.add(WeatherDto.builder()
                            .hour(timeElement.getText())
                            .weather(weatherElement.getText())
                            .temperature(temperatureParts)
                            .windChillTemperature(feelingTemperatureElement.getText())
                            .precipitation(windElement.getText())
                            .humidity(humidityElement.getText())
                            .build()
                    );

                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                driver.quit();
            }
            return weatherDtoList;
        }
        return null;
    }

    public List<WeatherByLocationDto> getLocationWeather() {
        String[] v = new String[5];
        Map<String, double[]> location = new HashMap<>();
        location.put("고척스카이돔", new double[]{126.8835, 37.5265});
        location.put("잠실종합운동장", new double[]{127.102193, 37.512048});
        location.put("삼성라이온즈파크", new double[]{128.6000, 35.8699});
        location.put("기아챔피언스필드", new double[]{126.8526, 35.1802});
        location.put("수원KT위즈파크", new double[]{127.0286, 37.2632});
        location.put("창원NC파크", new double[]{128.6812, 35.2353});
        location.put("인천SSG랜더스필드", new double[]{126.7031, 37.4600});
        location.put("한화생명이글스파크", new double[]{127.3842, 36.3502});
        location.put("부산사직야구장", new double[]{129.083041, 35.198362});

        List<WeatherByLocationDto> weatherList = new ArrayList<>();
        location.forEach((key, value) -> {
            int x = (int) value[0];
            int y = (int) value[1];
            LocationWeatherCrawler locationWeatherCrawler = new LocationWeatherCrawler();
            String s = locationWeatherCrawler.get(y, x, v);
            WeatherByLocationDto weatherByLocationDto = WeatherByLocationDto.builder()
                    .location(key)
                    .date(v[0])
                    .time(v[1])
                    .weather(v[2])
                    .temperature(v[3])
                    .humidity(v[4])
                    .build();
            if (s == null) {
                weatherList.add(weatherByLocationDto);
            }
        });
        return weatherList;
    }
}