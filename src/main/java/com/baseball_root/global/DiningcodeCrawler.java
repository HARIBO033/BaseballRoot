package com.baseball_root.global;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DiningcodeCrawler{

    private final String DININGCODE_URL = "https://www.diningcode.com/";
    private static final Logger logger = LoggerFactory.getLogger(DiningcodeCrawler.class);


    public ArrayList searchRestaurantByLocalName(String localName) throws IOException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/static/driver/chromedriver.exe");
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless"); // 브라우저 창을 띄우지 않음
        options.addArguments("--disable-gpu");// gpu 사용 안함
        options.addArguments("--no-sandbox");// sandbox 사용 안함
        options.addArguments("--disable-dev-shm-usage");// dev-shm 사용 안함
        options.addArguments("--lang=ko_KR");// 언어 설정
        options.addArguments("--enable-javascript");// 자바스크립트 사용
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");// user-agent 설정

        WebDriver driver = new ChromeDriver(options);

        ArrayList<RestaurantDto> restaurantList = new ArrayList<RestaurantDto>();

        try {
            String url = DININGCODE_URL + "list.dc?query=" + localName;
            //https://www.diningcode.com/search.dc?query=%EC%9E%A0%EC%8B%A4%EC%9A%B4%EB%8F%99%EC%9E%A5
            logger.info("page URL : " + url);
            logger.debug("page URL : " + url);
            driver.get(url);
            //System.out.println(driver.getPageSource());

            List<WebElement> restaurantElements = driver.findElements(By.cssSelector("#root > div > div.sc-fYlTny.cdYvzz.Scroll__List__Section > div.sc-hhyLtv.kCZanm.Poi__List__Wrap"));

            //System.out.println("restaurantElements : " + restaurantElements.get(0).getText());
            //System.out.println("restaurantSize " + restaurantElements.get(0).findElements(By.className("InfoHeader")).size());
            List<WebElement> restaurants = restaurantElements.get(0).findElements(By.className("RHeader"));

            for (int i = 0; i < restaurants.size(); i++) {
                try {
                    restaurantList.add(getRestaurant(restaurants.get(i)));
                    System.out.println("식당이름 : " + Arrays.toString(restaurantList.get(i).toStringArray()));
                } catch (IOException e) {
                    continue;
                }
            }
        } finally {
            driver.quit();
        }

        return restaurantList;
    }

    private RestaurantDto getRestaurant(WebElement element) throws IOException {

        String restaurantImageUrl = getRestaurantImageUrl(element);
        String restaurantName = getRestaurantName(element);
        String restaurantUserScore = getRestaurantUserScore(element);
        String restaurantWorkingTime = getRestaurantWorkingTime(element);
        String restaurantKeyword = getRestaurantKeyword(element);

        RestaurantDto restaurant = new RestaurantDto();
        restaurant.setRestaurantsImgUrl(restaurantImageUrl);
        restaurant.setRestaurantsName(restaurantName);
        restaurant.setRestaurantsUserScore(restaurantUserScore);
        restaurant.setRestaurantsKeyword(restaurantKeyword);
        restaurant.setRestaurantsWorkingTime(restaurantWorkingTime);

        return restaurant;
    }

    // 식당 이미지 URL 가져오기
    private String getRestaurantImageUrl(WebElement element) {
        List<WebElement> restaurantPictureElement = element.findElements(By.className("title"));
        return restaurantPictureElement.get(0).getAttribute("src");
    }

    // 식당 이름 가져오기
    private String getRestaurantName(WebElement element) {
        List<WebElement> restaurantNameElement = element.findElements(By.className("InfoHeader"));
        return restaurantNameElement.get(0).getText();
    }

    // 식당 평점 가져오기
    private String getRestaurantUserScore(WebElement element) {
        List<WebElement> restaurantTelElement = element.findElements(By.className("UserScore"));
        return restaurantTelElement.get(0).getText();
    }

    // 식당 영업시간 가져오기
    private String getRestaurantWorkingTime(WebElement element) {
        List<WebElement> restaurantTelElement = element.findElements(By.className("OpenStatus"));
        return restaurantTelElement.get(0).getText();
    }

    // 식당 키워드 가져오기
    private String getRestaurantKeyword(WebElement element) {
        List<WebElement> restaurantTelElement = element.findElements(By.className("Category"));
        return restaurantTelElement.get(0).getText();
    }

}
