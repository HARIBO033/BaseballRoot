package com.baseball_root.diary.service;

import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    /*public List<DiaryDto.Response> getDiaryList(Long memberId, String date){
        // 날짜와 member 를 받아와서 해당 날짜의 일기 리스트를 반환
        return diaryRepository.findByMemberIdAndDate(memberId, date)
                .stream()
                .map(DiaryDto.Response::fromEntity)
                .toList();
    }*/

    public DiaryDto.Response getDetailDiary(Long id) {
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + id));
        return DiaryDto.Response.fromEntity(diary);
    }
    @Transactional
    public DiaryDto.Response saveDiary(DiaryDto.Request diaryDto){
        Diary diary = diaryDto.toEntity(
                diaryDto.getImageUrl(),
                diaryDto.getHomeVsAway(),
                diaryDto.getPlace(),
                diaryDto.getSeat(),
                diaryDto.getTitle(),
                diaryDto.getContent(),
                diaryDto.getLineUp(),
                diaryDto.getMvp(),
                diaryDto.getAuthor());
        diaryRepository.save(diary);
        return DiaryDto.Response.fromEntity(diary);
    }

    public DiaryDto.Response updateDiary(Long id, DiaryDto.Request diaryDto){
        System.out.println("diaryDto = " + diaryDto);
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + id));
        diary.update(
                diaryDto.getImageUrl(),
                diaryDto.getSeat(),
                diaryDto.getTitle(),
                diaryDto.getContent(),
                diaryDto.getLineUp(),
                diaryDto.getMvp()
        );
        diaryRepository.flush();
        return DiaryDto.Response.fromEntity(diary);
    }

    public Long deleteDiary(long id){
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + id));
        diaryRepository.delete(diary);
        return id;
    }
}
