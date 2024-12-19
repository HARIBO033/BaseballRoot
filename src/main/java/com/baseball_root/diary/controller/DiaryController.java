package com.baseball_root.diary.controller;

import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.service.DiaryService;
import com.baseball_root.crawler.ScheduleDto;
import com.baseball_root.crawler.WebCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    /**
     * @param memberId
     * @param diaryDto
     * @param files
     * @return
     * @throws Exception
     * @RequestBody : JSON 형태로 데이터를 받음
     * @RequestPart : 파일을 받을 때 사용
     * @PathVariable : URL 경로에 변수를 넣어서 사용
     * @RequestParam : URL 쿼리스트링으로 데이터를 받음
     */
    @PostMapping(value = "/save/{memberId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DiaryDto.Response createDiary(@PathVariable(name = "memberId") Long memberId,
                                         @RequestPart(value = "diaryDto") DiaryDto.Request diaryDto,
                                         @RequestPart(required = false , value = "files")List<MultipartFile> files){
        log.info("request : {}", diaryDto);

        return diaryService.saveDiary(memberId, diaryDto, files);
    }

    /**
     * @param diaryId
     * @param diaryDto
     * @return DiaryDto.Response
     */
    @PutMapping("/{diaryId}")
    public DiaryDto.Response updateDiary(@PathVariable("diaryId") Long diaryId,
                                         @RequestPart(value = "diaryDto") DiaryDto.Request diaryDto,
                                         @RequestPart(required = false , value = "files")List<MultipartFile> files){
        log.info("@@ CONTROLLER TEST request : {}", diaryDto);
        return diaryService.updateDiary(diaryId, diaryDto, files);
    }

    @DeleteMapping("/{diaryId}")
    public Long deleteDiary(@PathVariable("diaryId") long diaryId){
        log.info("delete id : {}", diaryId);
        return diaryService.deleteDiary(diaryId);
    }
}
