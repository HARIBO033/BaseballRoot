package com.baseball_root.diary.controller;

import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.service.DiaryService;
import com.baseball_root.crawler.ScheduleDto;
import com.baseball_root.crawler.WebCrawler;
import com.baseball_root.global.response.CommonResponse;
import com.baseball_root.global.response.SuccessCode;
import jakarta.validation.Valid;
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

    // 일정 크롤링
    @GetMapping("/crawling/monthly/{date}")//202410
    public CommonResponse<List<ScheduleDto>> getMonthlyGameScheduleList(@PathVariable(name = "date") String date) {
        log.info("getMonthlyGameScheduleList 호출 date : {}", date);
        List<ScheduleDto> scheduleDtoList = null;
        try {
            WebCrawler webCrawler = new WebCrawler();
            scheduleDtoList = webCrawler.scrapeSchedule(date);
        } catch (IllegalArgumentException e) {
            log.error("해당 날짜에 일정이 없습니다.");
        }
        return CommonResponse.success(SuccessCode.GET_CRAWLING_SUCCESS, scheduleDtoList);

    }

    @GetMapping("/{diaryId}")
    public CommonResponse<?> getDetailDiary(@PathVariable(name = "diaryId") Long diaryId) {
        //diaryService.getDetailDiary(diaryId);
        log.info("getDetailDiary 호출 diaryId : {}", diaryId);
        return CommonResponse.success(SuccessCode.GET_POST_SUCCESS, diaryService.getDetailDiary(diaryId));
    }

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
    public CommonResponse<?> createDiary(@PathVariable(name = "memberId") Long memberId,
                                         @RequestPart(value = "diaryDto") @Valid DiaryDto.Request diaryDto,
                                         @RequestPart(required = false, value = "files") List<MultipartFile> files) {
        log.info("createDiary invoke, request : {}", diaryDto);

        return CommonResponse.success(SuccessCode.POST_POST_SUCCESS, diaryService.saveDiary(memberId, diaryDto, files));
    }

    /**
     * @param diaryId
     * @param diaryDto
     * @return DiaryDto.Response
     */
    @PutMapping(value = "/{diaryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<?> updateDiary(@PathVariable("diaryId") Long diaryId,
                                         @RequestPart(value = "diaryDto") DiaryDto.Request diaryDto,
                                         @RequestPart(required = false, value = "files") List<MultipartFile> files) {
        log.info("updateDiary invoke,  request : {}", diaryDto);
        return CommonResponse.success(SuccessCode.UPDATE_POST_SUCCESS, diaryService.updateDiary(diaryId, diaryDto, files));
    }

    @DeleteMapping("/{diaryId}")
    public CommonResponse<?> deleteDiary(@PathVariable("diaryId") long diaryId) {
        log.info("deleteDiary invoke, delete id : {}", diaryId);
        diaryService.deleteDiary(diaryId);
        return CommonResponse.success(SuccessCode.DELETE_POST_SUCCESS);
    }

}
