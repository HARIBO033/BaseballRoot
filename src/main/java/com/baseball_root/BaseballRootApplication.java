package com.baseball_root;

import com.baseball_root.global.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class BaseballRootApplication {
	public static void main(String[] args) throws IOException {

        SpringApplication.run(BaseballRootApplication.class, args);

		/*// KBO
		WebCrawler webCrawler = new WebCrawler();
		List<ScheduleDto> scheduleDtoList = webCrawler.scrapeSchedule("20240522");

		for (ScheduleDto scheduleDto : scheduleDtoList) {
			System.out.println(scheduleDto);
		}
*/
        //DiningcodeCrawler diningcodeCrawler = new DiningcodeCrawler();
        //System.out.println(diningcodeCrawler.searchRestaurantByLocalName("잠실운동장"));



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

	}
}
