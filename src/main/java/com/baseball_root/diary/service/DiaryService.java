package com.baseball_root.diary.service;

import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public void getDiary(Long id){
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + id));
    }

    public void saveDiary(DiaryDto.Request diaryDto){
        Diary diary = diaryDto.toEntity(diaryDto.getTitle(), diaryDto.getContent(), diaryDto.getAuthor());
        diaryRepository.save(diary);
    }

    public void updateDiary(Long id, DiaryDto.Request diaryDto){
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + id));

    }

    public void deleteDiary(Long id){
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + id));
        diaryRepository.delete(diary);
    }
}
