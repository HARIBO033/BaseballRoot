package com.baseball_root.diary.service;

import com.baseball_root.attach.AttachImage;
import com.baseball_root.attach.AttachImageRepository;
import com.baseball_root.crawler.ScheduleDto;
import com.baseball_root.crawler.WebCrawler;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.repository.DiaryRepository;
import com.baseball_root.global.S3Service;
import com.baseball_root.global.exception.custom_exception.InvalidMemberIdException;
import com.baseball_root.global.exception.custom_exception.InvalidPostIdException;
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final AttachImageRepository attachImageRepository;
    private final S3Service s3Service;
    private final WebCrawler webCrawler;

    private final CacheManager cacheManager;

    @Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 3시
    public void clearKboScheduleCache() {
        cacheManager.getCache("kboSchedule").clear();
        System.out.println("🧹 KBO 스케줄 캐시 초기화됨 (매일 3시)");
    }

    @Cacheable(value = "kboSchedule", key = "#p0", unless = "#result == null or #result.isEmpty()")
    public List<ScheduleDto> getKboGameScheduleList(String date) {
        // 일정 크롤링
        log.info("getKboGameScheduleList 호출 date : {}", date);
        List<ScheduleDto> scheduleDtoList = null;
        try {
            scheduleDtoList = webCrawler.scrapeSchedule(date);
        } catch (IllegalArgumentException e) {
            log.error("해당 날짜에 일정이 없습니다");
        }
        return scheduleDtoList;
    }

    public DiaryDto.Response getDetailDiary(Long id) {
        Diary diary = diaryRepository.findById(id).orElseThrow(InvalidPostIdException::new);
        List<String> attachImageUrls = getAttachImageUrls(id);
        return DiaryDto.Response.fromEntity(diary, attachImageUrls);
    }

    @Transactional
    public DiaryDto.Response saveDiary(Long memberId, DiaryDto.Request diaryDto, List<MultipartFile> files) {
        Member author = memberRepository.findById(memberId)
                .orElseThrow(InvalidMemberIdException::new);
        Diary diary = getBuild(diaryDto, author);
        diaryRepository.save(diary);

        if (files != null && !files.isEmpty()) {
            List<String> ImageNames = s3Service.uploadMultiFile(files);
            for (String imageName : ImageNames) {
                String imageUrl = s3Service.getFileUrl(imageName);
                AttachImage attachImage = AttachImage.builder()
                        .url(imageUrl)
                        .name(imageName)
                        .diary(diary)
                        .build();
                attachImageRepository.save(attachImage);
            }
            List<String> attachImageUrls = getAttachImageUrls(diary.getId());
            return DiaryDto.Response.fromEntity(diary, attachImageUrls);
        }
        return DiaryDto.Response.fromEntity(diary, List.of());
    }


    @Transactional
    public DiaryDto.Response updateDiary(Long id, DiaryDto.Request diaryDto, List<MultipartFile> files) {
        Diary diary = diaryRepository.findById(id).orElseThrow(InvalidPostIdException::new);
        diary.update(
                diaryDto.getSeat(),
                diaryDto.getTitle(),
                diaryDto.getContent(),
                diaryDto.getLineUp(),
                diaryDto.getMvp()
        );
        diaryRepository.flush();

        List<String> attachImageUrls = getAttachImageUrls(id);
        return DiaryDto.Response.fromEntity(diary, attachImageUrls);
    }

    public Long deleteDiary(long id) {
        Diary diary = diaryRepository.findById(id).orElseThrow(InvalidPostIdException::new);
        diary.getAttachImages().forEach(attachImage -> {
            s3Service.deleteFile(attachImage.getName());
        });
        diaryRepository.delete(diary);
        return id;
    }

    // Repository 에서 해당 일기에 속한 이미지 url 리스트 반환
    public List<String> getAttachImageUrls(Long diaryId) {
        List<AttachImage> attachImages = attachImageRepository.findByDiaryId(diaryId);
        return attachImages.stream().map(AttachImage::getUrl).toList();
    }

    private static Diary getBuild(DiaryDto.Request diaryDto, Member author) {
        return Diary.builder()
                .home(diaryDto.getHome())
                .away(diaryDto.getAway())
                .place(diaryDto.getPlace())
                .seat(diaryDto.getSeat())
                .title(diaryDto.getTitle())
                .content(diaryDto.getContent())
                .lineUp(diaryDto.getLineUp())
                .mvp(diaryDto.getMvp())
                .member(author)
                .location(diaryDto.getLocation())
                .gameResult(diaryDto.getGameResult())
                .gameDate(diaryDto.getGameDate())
                .build();
    }


}
