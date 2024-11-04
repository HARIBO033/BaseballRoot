package com.baseball_root.game;


import com.baseball_root.crawler.ScheduleDto;
import com.baseball_root.crawler.WebCrawler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultGameService {

    private final ResultGameRepository resultGameRepository;

    public void saveResultGame() {
        WebCrawler webCrawler = new WebCrawler();

        String year, month;
        for (int i = 2023; i <= 2024; i++) {
            year = String.valueOf(i);
            for (int j = 1; j <= 11; j++) {
                if (j < 10) {
                    month = "0" + String.valueOf(j);
                } else {
                    month = String.valueOf(j);
                }
                String temp = year + month;
                System.out.println(temp);
                List<ScheduleDto> scheduleDtoList = webCrawler.scrapeSchedule(year + month);

                scheduleDtoList.stream().map(scheduleDto -> {
                    ResultGame resultGame = new ResultGame();
                    resultGame.setDate(scheduleDto.getCurrentDay());
                    resultGame.setTime(scheduleDto.getTime());
                    resultGame.setHomeTeam(scheduleDto.getTeam1());
                    resultGame.setAwayTeam(scheduleDto.getTeam2());
                    //years.selectByValue(date.substring(0, 4));
                    //System.out.println("rrrrrrrrrrrr" + scheduleDto.getVs().split("[^0-9]+")[0]);
                    resultGame.setHomeScore(Integer.valueOf(scheduleDto.getVs().split("[^0-9]+")[0]));
                    resultGame.setAwayScore(Integer.valueOf(scheduleDto.getVs().split("[^0-9]+")[1]));
                    resultGame.setLocation(scheduleDto.getLocation());
                    //resultGameRepository.save(resultGame);
                    return resultGame;
                }).forEach(resultGameRepository::save);
            }
        }


    }
}
