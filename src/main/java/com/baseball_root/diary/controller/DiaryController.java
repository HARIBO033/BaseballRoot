package com.baseball_root.diary.controller;

import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.service.DiaryService;
import com.baseball_root.crawler.ScheduleDto;
import com.baseball_root.crawler.WebCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ScheduleDto>> getMonthlyGameScheduleList(@PathVariable(name = "date") String date) {
        log.info("getMonthlyGameScheduleList 호출 date : {}", date);
        List<ScheduleDto> scheduleDtoList = null;
        try {
            WebCrawler webCrawler = new WebCrawler();
            scheduleDtoList = webCrawler.scrapeSchedule(date);
        } catch (IllegalArgumentException e) {
            log.error("해당 날짜에 일정이 없습니다.");
        }
        return ResponseEntity.ok(scheduleDtoList);

    }


    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryDto.Response> getDetailDiary(@PathVariable(name = "diaryId") Long diaryId) {
        //diaryService.getDetailDiary(diaryId);
        log.info("getDetailDiary 호출 diaryId : {}", diaryId);
        return ResponseEntity.ok(diaryService.getDetailDiary(diaryId));
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
    public ResponseEntity<DiaryDto.Response> createDiary(@PathVariable(name = "memberId") Long memberId,
                                                         @RequestPart(value = "diaryDto") DiaryDto.Request diaryDto,
                                                         @RequestPart(required = false, value = "files") List<MultipartFile> files) {
        log.info("createDiary invoke, request : {}", diaryDto);

        //diaryService.saveDiary(memberId, diaryDto, files);
        return ResponseEntity.ok(diaryService.saveDiary(memberId, diaryDto, files));
    }

    /**
     * @param diaryId
     * @param diaryDto
     * @return DiaryDto.Response
     */
    @PutMapping(value = "/{diaryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DiaryDto.Response> updateDiary(@PathVariable("diaryId") Long diaryId,
                                                         @RequestPart(value = "diaryDto") DiaryDto.Request diaryDto,
                                                         @RequestPart(required = false, value = "files") List<MultipartFile> files) {
        log.info("updateDiary invoke,  request : {}", diaryDto);
        //diaryService.updateDiary(diaryId, diaryDto, files);
        return ResponseEntity.ok(diaryService.updateDiary(diaryId, diaryDto, files));
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<String> deleteDiary(@PathVariable("diaryId") long diaryId) {
        log.info("deleteDiary invoke, delete id : {}", diaryId);
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.ok("다이어리가 삭제되었습니다.");
    }
}
