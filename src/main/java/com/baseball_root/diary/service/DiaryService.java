package com.baseball_root.diary.service;

import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.repository.DiaryRepository;
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
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
        Member author = memberRepository.findByNickname(diaryDto.getNickname());
        /*Diary diary = diaryDto.toEntity(
                diaryDto.getImageUrl(),
                diaryDto.getHomeVsAway(),
                diaryDto.getPlace(),
                diaryDto.getSeat(),
                diaryDto.getTitle(),
                diaryDto.getContent(),
                diaryDto.getLineUp(),
                diaryDto.getMvp(),
                author
        );*/
        Diary diary  = Diary.builder()
                .imageUrl(diaryDto.getImageUrl())
                .homeVsAway(diaryDto.getHomeVsAway())
                .place(diaryDto.getPlace())
                .seat(diaryDto.getSeat())
                .title(diaryDto.getTitle())
                .content(diaryDto.getContent())
                .lineUp(diaryDto.getLineUp())
                .mvp(diaryDto.getMvp())
                .member(author)
                .build();
        diaryRepository.save(diary);
        return DiaryDto.Response.fromEntity(diary);
    }

    @Transactional
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
