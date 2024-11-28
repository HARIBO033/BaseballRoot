package com.baseball_root.diary.controller;

import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.service.DiaryService;
import com.baseball_root.crawler.ScheduleDto;
import com.baseball_root.crawler.WebCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    //캘린더에서 날짜를 선택하면 리스트를 반환
    @GetMapping("/crawling/monthly/{date}")//202410
    public List<ScheduleDto> getMonthlyGameScheduleList(@PathVariable(name = "date") String date){
        WebCrawler webCrawler = new WebCrawler();
        List<ScheduleDto> scheduleDtoList = webCrawler.scrapeSchedule(date);
        if (scheduleDtoList.isEmpty()){
            return (List<ScheduleDto>) new IllegalArgumentException("해당 날짜에 일정이 없습니다.");
        }
        return scheduleDtoList;
    }


    @GetMapping("/{diaryId}")
    public DiaryDto.Response getDetailDiary(@PathVariable(name = "diaryId") Long diaryId){
        return diaryService.getDetailDiary(diaryId);
    }

    /*@PostMapping("/save1")
    public DiaryDto.Response createDiaryPage1(@RequestBody DiaryDto.Request diaryDto){
        log.info("request : {}", diaryDto);
        return diaryService.saveDiaryPage1(diaryDto);
    }*/
    @PostMapping("/save/{memberId}")
    public DiaryDto.Response createDiary(@PathVariable(name = "memberId") Long memberId,
                                         @RequestBody DiaryDto.Request diaryDto){
        log.info("request : {}", diaryDto);
        return diaryService.saveDiary(memberId, diaryDto);
    }

    @PutMapping("/{diaryId}")
    public DiaryDto.Response updateDiary(@PathVariable("diaryId") Long diaryId, @RequestBody DiaryDto.Request diaryDto){
        log.info("@@ CONTROLLER TEST request : {}", diaryDto);
        return diaryService.updateDiary(diaryId, diaryDto);
    }

    @DeleteMapping("/{diaryId}")
    public Long deleteDiary(@PathVariable("diaryId") long diaryId){
        log.info("delete id : {}", diaryId);
        return diaryService.deleteDiary(diaryId);
    }
}
