package com.baseball_root.diary.dto;

import com.baseball_root.diary.domain.Diary;
import com.baseball_root.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Builder
public class DiaryDto {

    @Getter
    @Setter
    public static class Request{
        @NotBlank
        private String imageUrl;
        @NotBlank
        private String homeVsAway;
        @NotBlank
        private String place;
        private String seat;
        @NotBlank
        private String title;
        private String content;
        private String lineUp;
        private String mvp;

        private String nickname;

        public Diary toEntity(String imageUrl, String homeVsAway, String place, String seat, String title, String content, String lineUp, String mvp, Member member){
            return new Diary(imageUrl, homeVsAway, place, seat, title, content, lineUp, mvp, member);
        }
    }

    @Builder
    @Getter
    public static class Response{
        private String imageUrl;
        private String homeVsAway;
        private String place;
        private String seat;
        private String title;
        private String content;
        private String lineUp;
        private String mvp;
        private String nickname;

        public static Response fromEntity(Diary diary){
            return Response.builder()
                    .imageUrl(diary.getImageUrl())
                    .homeVsAway(diary.getHomeVsAway())
                    .place(diary.getPlace())
                    .seat(diary.getSeat())
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .lineUp(diary.getLineUp())
                    .mvp(diary.getMvp())
                    .nickname(diary.getMember().getNickname())
                    .build();
        }

    }
}
