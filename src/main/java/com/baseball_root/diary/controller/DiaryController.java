package com.baseball_root.diary.controller;

import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.service.DiaryService;
import com.baseball_root.global.ScheduleDto;
import com.baseball_root.global.WebCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diarys")
public class DiaryController {
    private final DiaryService diaryService;

    //캘린더에서 날짜를 선택하면 리스트를 반환
    @GetMapping("/{date}")//20240705
    public List<ScheduleDto> findGameByDate(@PathVariable String date){
        WebCrawler webCrawler = new WebCrawler();
        return webCrawler.scrapeSchedule(date);
    }


    @GetMapping("/{id}")
    public DiaryDto.Response getDetailDiary(@PathVariable Long id){
        return diaryService.getDetailDiary(id);
    }

    @PostMapping("/save")
    public DiaryDto.Response createDiary(@RequestBody DiaryDto.Request diaryDto){
        log.info("request : {}", diaryDto);
        return diaryService.saveDiary(diaryDto);
    }

    @PutMapping("/{id}")
    public DiaryDto.Response updateDiary(@PathVariable("id") Long id, @RequestBody DiaryDto.Request diaryDto){
        log.info("@@ CONTROLLER TEST request : {}", diaryDto);
        return diaryService.updateDiary(id, diaryDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteDiary(@PathVariable("id") long id){
        log.info("delete id : {}", id);
        return diaryService.deleteDiary(id);
    }
}
