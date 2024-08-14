package com.baseball_root.diary.dto;

import com.baseball_root.diary.domain.Diary;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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

        private String author;//todo: 작성자 타입 객체로 변경

        public Diary toEntity(String imageUrl, String homeVsAway, String place, String seat, String title, String content, String lineUp, String mvp, String author){
            return new Diary(imageUrl, homeVsAway, place, seat, title, content, lineUp, mvp, author);
        }
    }


    public static class Response{
        private Long id;
        private String title;
        private String content;
        private String author;
    }
}
