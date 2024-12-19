package com.baseball_root.diary.service;

import com.baseball_root.attach.AttachImage;
import com.baseball_root.attach.AttachImageRepository;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.diary.repository.DiaryRepository;
import com.baseball_root.global.S3Service;
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final AttachImageRepository attachImageRepository;
    private final S3Service s3Service;
    /*public List<DiaryDto.Response> getDiaryList(Long memberId, String date){
        // 날짜와 member 를 받아와서 해당 날짜의 일기 리스트를 반환
        return diaryRepository.findByMemberIdAndDate(memberId, date)
                .stream()
                .map(DiaryDto.Response::fromEntity)
                .toList();
    }*/

    public DiaryDto.Response getDetailDiary(Long id) {
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + id));
        List<String> attachImageUrls = getAttachImageUrls(id);
        return DiaryDto.Response.fromEntity(diary, attachImageUrls);
    }

    @Transactional
    public DiaryDto.Response saveDiary(Long memberId, DiaryDto.Request diaryDto, List<MultipartFile> files){
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + memberId));
        Diary diary  = Diary.builder()
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
        diaryRepository.save(diary);

        List<String> ImageNames = s3Service.uploadMultiFile(files);
        for (String imageName : ImageNames){
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

    @Transactional
    public DiaryDto.Response updateDiary(Long id, DiaryDto.Request diaryDto, List<MultipartFile> files){
        System.out.println("diaryDto = " + diaryDto);
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + id));
        diary.update(
                diaryDto.getSeat(),
                diaryDto.getTitle(),
                diaryDto.getContent(),
                diaryDto.getLineUp(),
                diaryDto.getMvp()
        );
        diaryRepository.flush();

        List<String> attachImageUrls = getAttachImageUrls(id);
        return DiaryDto.Response.fromEntity(diary,attachImageUrls);
    }
    public Long deleteDiary(long id){
        Diary diary = diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일기가 없습니다. id=" + id));
        diary.getAttachImages().forEach(attachImage -> {
            s3Service.deleteFile(attachImage.getName());
        });
        diaryRepository.delete(diary);
        return id;
    }

    // Repository 에서 해당 일기에 속한 이미지 url 리스트 반환
    public List<String> getAttachImageUrls(Long diaryId){
        List<AttachImage> attachImages = attachImageRepository.findByDiaryId(diaryId);
        return attachImages.stream().map(AttachImage::getUrl).toList();
    }
}
