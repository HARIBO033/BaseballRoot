package com.baseball_root.crawler;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class WebCrawler {
    public List<ScheduleDto> scrapeSchedule(String date) {
        List<ScheduleDto> scheduleList = new ArrayList<>();

        WebDriverManager.chromedriver().setup(); // WebDriverManager를 사용하면 별도로 드라이버를 다운로드 받지 않아도 됨
        //System.setProperty("webdriver.chrome.driver", "src/main/resources/static/driver/chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Headless 모드
        options.addArguments("--no-sandbox"); // 샌드박스 비활성화
        options.addArguments("--disable-dev-shm-usage"); // /dev/shm 사용 비활성화
        options.addArguments("--disable-gpu"); // GPU 사용 비활성화
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.koreabaseball.com/Schedule/Schedule.aspx");

            Select years = new Select(driver.findElement(By.id("ddlYear")));
            Select months = new Select(driver.findElement(By.id("ddlMonth")));
            Select league = new Select(driver.findElement(By.id("ddlSeries")));

            years.selectByValue(date.substring(0, 4));//01234567
            months.selectByValue(date.substring(4, 6));
            String strMonth = date.substring(4, 6);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@months : " + strMonth);
            switch (strMonth) {
                case "10", "11" -> {
                    league.selectByValue("3,4,5,7");

                    //Jsoup, selenium 라이브러리 -> 크롤링 라이브러리
                    Document doc = Jsoup.parse(driver.getPageSource());
                    Elements baseballSchedule = doc.select("#tblScheduleList > tbody > tr");

                    String currentDay = null;
                    for (Element schedule : baseballSchedule) {
                        Element day = schedule.selectFirst("td.day");
                        Element time = schedule.selectFirst("td.time");
                        Element team1 = schedule.selectFirst("td.play > span");
                        Element vs = schedule.selectFirst("td.play > em");
                        Element team2 = schedule.selectFirst("td.play > span:nth-child(3)");
                        Element location = schedule.selectFirst("td:nth-child(8)");

                        // team1과 team2 텍스트를 TeamName 타입으로 변환
                        TeamName teamName1 = team1 != null ? TeamName.fromKoreanName(team1.text()) : TeamName.UNKNOWN;
                        TeamName teamName2 = team2 != null ? TeamName.fromKoreanName(team2.text()) : TeamName.UNKNOWN;


                        if (day == null) {
                            scheduleList.add(null);
                            break;
                        }
                        if (location == null || "-".equals(location.text())) {
                            location = schedule.selectFirst("td:nth-child(7)");
                        }

                        if (day != null && (currentDay == null || !currentDay.equals(day.text()))) {
                            currentDay = day.text();
                        }


                        if (time != null) {
                            ScheduleDto dto = new ScheduleDto(
                                    currentDay,
                                    time.text(),
                                    teamName1,
                                    vs != null ? vs.text() : "-", // vs가 null일 경우 기본값 "-"
                                    teamName2,
                                    location != null ? location.text() : "-"
                            );
                            scheduleList.add(dto);
                        }
                    }
                    break;
                }
                case "03", "04", "05", "06", "07", "08", "09" -> {
                    league.selectByValue("0,9,6");

                    //Jsoup, selenium 라이브러리 -> 크롤링 라이브러리
                    Document doc = Jsoup.parse(driver.getPageSource());
                    Elements baseballSchedule = doc.select("#tblScheduleList > tbody > tr");

                    String currentDay = null;
                    for (Element schedule : baseballSchedule) {
                        Element day = schedule.selectFirst("td.day");
                        Element time = schedule.selectFirst("td.time");
                        Element team1 = schedule.selectFirst("td.play > span");
                        Element vs = schedule.selectFirst("td.play > em");
                        Element team2 = schedule.selectFirst("td.play > span:nth-child(3)");
                        Element location = schedule.selectFirst("td:nth-child(8)");

                        // team1과 team2 텍스트를 TeamName 타입으로 변환
                        TeamName teamName1 = team1 != null ? TeamName.fromKoreanName(team1.text()) : TeamName.UNKNOWN;
                        TeamName teamName2 = team2 != null ? TeamName.fromKoreanName(team2.text()) : TeamName.UNKNOWN;

                        if (location == null || "-".equals(location.text())) {
                            location = schedule.selectFirst("td:nth-child(7)");
                        }

                        if (day != null && (currentDay == null || !currentDay.equals(day.text()))) {
                            currentDay = day.text();
                        }


                        if (time != null) {
                            ScheduleDto dto = new ScheduleDto(
                                    currentDay,
                                    time.text(),
                                    teamName1,
                                    vs != null ? vs.text() : "-",
                                    teamName2,
                                    location != null ? location.text() : "-"
                            );
                            scheduleList.add(dto);
                        }
                    }

                    break;
                }
                case "01", "02", "12" -> scheduleList.add(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return scheduleList;
    }


}
