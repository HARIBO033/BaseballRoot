package com.baseball_root.global;

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
    public List<ScheduleDto> scrapeSchedule(String year, String month) {
        List<ScheduleDto> scheduleList = new ArrayList<>();

        System.setProperty("webdriver.chrome.driver", "src/main/resources/static/driver/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.koreabaseball.com/Schedule/Schedule.aspx");

            Select years = new Select(driver.findElement(By.id("ddlYear")));
            Select months = new Select(driver.findElement(By.id("ddlMonth")));

            years.selectByValue(year);
            months.selectByValue(month);
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

                if ("-".equals(location.text())) {
                    location = schedule.selectFirst("td:nth-child(7)");
                }

                if (day != null && (currentDay == null || !currentDay.equals(day.text()))) {
                        currentDay = day.text();
                    }


                if (time != null) {
                    ScheduleDto dto = new ScheduleDto(currentDay, time.text(), team1.text(), vs.text(), team2.text(), location.text());
                    scheduleList.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return scheduleList;
    }
}
