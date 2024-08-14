package com.baseball_root.diary.controller;

import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.service.DiaryService;
import com.baseball_root.global.ScheduleDto;
import com.baseball_root.global.WebCrawler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    //캘린더에서 날짜를 선택하면 리스트를 반환
    @GetMapping("/{date}")//20240705
    public List<ScheduleDto> findGameByDate(@PathVariable String date){
        WebCrawler webCrawler = new WebCrawler();
        return webCrawler.scrapeSchedule(date);
    }


    @GetMapping("/{id}")
    public void getDiary(@PathVariable Long id, @PathVariable String date){
        diaryService.getDiary(id);
    }

    @PostMapping("/")
    public void createDiary(@RequestBody DiaryDto.Request diaryDto){
        diaryService.saveDiary(diaryDto);
    }

    @PutMapping("/{id}")
    public void updateDiary(@PathVariable Long id){
    }

    @DeleteMapping("/{id}")
    public void deleteDiary(@PathVariable Long id){
    }
}
