package com.baseball_root.crawler;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherCrawler_v2 {

    /*public void getWeatherInfo() {
        Map<String, String> locationKeywords = new HashMap<>();
        locationKeywords.put("고척 스카이돔", "서울 구로구");
        locationKeywords.put("잠실운동장", "서울 송파구");
        locationKeywords.put("삼성 라이온즈 파크", "대구 수성구");
        locationKeywords.put("기아 챔피언스필드", "광주 북구 임동");
        locationKeywords.put("수원 KT위즈파크", "경기 수원시");
        locationKeywords.put("창원 NC파크", "경남 창원시");
        locationKeywords.put("인천 SSG 랜더스필드", "인천 미추홀구");

        for (String location : locationKeywords.keySet()) {
            try {
                String keyword = locationKeywords.get(location);
                System.out.println("\n[" + location + "] 날씨 정보:");
                fetchWeatherInfo(keyword);
            } catch (Exception e) {
                System.err.println("Error fetching weather for " + location + ": " + e.getMessage());
            }
        }
    }*/
    public static void fetchWeatherInfo(String keyword) throws IOException {
        //System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Headless 모드
        options.addArguments("--no-sandbox"); // 샌드박스 비활성화
        options.addArguments("--disable-dev-shm-usage"); // /dev/shm 사용 비활성화
        options.addArguments("--disable-gpu"); // GPU 사용 비활성화
        WebDriver driver = new ChromeDriver(options);
        // 크롤링할 URL (기상청 또는 날씨 제공 사이트)
        try {
            driver.get("https://www.weather.go.kr/w/index.do#dong/2817771000/37.436998685442084/126.69327612453377/%EC%9D%B8%EC%B2%9C%20%EB%AF%B8%EC%B6%94%ED%99%80%EA%B5%AC%20%EB%AC%B8%ED%95%99%EB%8F%99/SCH/%EC%9D%B8%EC%B2%9CSSG%EB%9E%9C%EB%8D%94%EC%8A%A4%ED%95%84%EB%93%9C");
            WebElement clickATag = driver.findElement(By.xpath("/html/body/div[3]/section/div/div[2]/div[5]/div[3]/div/div[3]/div[1]/div[1]/div[3]/div[1]/div/div/a[2]"));
            clickATag.click();
            Thread.sleep(1000);

            Document document = Jsoup.parse(driver.getPageSource());

            driver.switchTo().defaultContent();
            List<WebElement> webElementList = driver.findElements(By.xpath("/html/body/div[3]/section/div/div[2]/div[5]/div[3]/div/div[3]/div[1]/div[1]/div[5]/div[2]/div[1]/div[1]/div/div[2]/ul"));

            System.out.println("INVOKE ELEMENTS@@@@@@@@@@@@@@@@@@");
            for (WebElement webElement : webElementList) {
                System.out.println(webElement.getText());
                WebElement timeElement = webElement.findElement(By.xpath("./li[1]/span[2]"));//시간
                WebElement weatherElement = webElement.findElement(By.xpath("./li[2]/span[2]"));//날씨
                //WebElement temperatureElement = webElement.findElement(By.className("hid feel"));//기온
                WebElement temperatureElement = webElement.findElement(By.xpath("./li[3]/span[1]"));//기온
                WebElement feelingTemperatureElement = webElement.findElement(By.xpath("./li[4]/span[2]"));//체감온도
                WebElement humidityElement = webElement.findElement(By.xpath("./li[8]/span[3]"));//바람
                WebElement windElement = webElement.findElement(By.xpath("./li[9]/span[2]"));//습도

                if (timeElement == null ){
                    break;
                }
                System.out.println(
                        "시간 : " + timeElement.getText()
                        + " / 날씨 : " + weatherElement.getText()
                        + " / 기온 : " + temperatureElement.getText()
                        //+ " / 체감기온 : " + feelingTemperatureElement.getText()
                        + " / 체감온도 : " + feelingTemperatureElement.getText()
                        + " / 바람 : " + humidityElement.getText()
                        + " / 습도 : " + windElement.getText() + "\n"
                        + " ======================================================================================"
                );
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

}
