package com.baseball_root.diary.dto;

import com.baseball_root.diary.domain.Diary;
import com.baseball_root.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
public class DiaryDto {



    @Getter
    @Setter
    public static class Request{
        @NotBlank
        private String imageUrl;
        private String home;
        private String away;
        @NotBlank
        private String place;
        private String seat;
        @NotBlank
        private String title;
        private String content;
        private String lineUp;
        private String mvp;

        private String location;
        private String gameResult;
        private String gameDate;

        public Diary toEntity(String imageUrl, String home, String away,String place, String seat, String title, String content, String lineUp, String mvp, Member member){
            return new Diary(imageUrl, home,away, place, seat, title, content, lineUp, mvp, member);
        }
    }

    @Builder
    @Getter
    public static class Response{
        private String imageUrl;
        private String home;
        private String away;
        private String place;
        private String seat;
        private String title;
        private String content;
        private String lineUp;
        private String mvp;
        private String location;
        private String gameResult;
        private String gameDate;
        private String nickname;
        private LocalDateTime createdAt;

        private Response(String imageUrl, String home, String away, String place, String seat, String title, String content, String lineUp, String mvp, String location, String gameResult, String gameDate, String nickname, LocalDateTime createdAt) {
            this.imageUrl = imageUrl;
            this.home = home;
            this.away = away;
            this.place = place;
            this.seat = seat;
            this.title = title;
            this.content = content;
            this.lineUp = lineUp;
            this.mvp = mvp;
            this.location = location;
            this.gameResult = gameResult;
            this.gameDate = gameDate;
            this.nickname = nickname;
            this.createdAt = createdAt;
        }

        public static Response of(Diary diary){
            return new Response(
                    diary.getImageUrl(),
                    diary.getHome(),
                    diary.getAway(),
                    diary.getPlace(),
                    diary.getSeat(),
                    diary.getTitle(),
                    diary.getContent(),
                    diary.getLineUp(),
                    diary.getMvp(),
                    diary.getLocation(),
                    diary.getGameResult(),
                    diary.getGameDate(),
                    diary.getMember().getNickname(),
                    diary.getCreatedAt());
        }

        public static Response fromEntity(Diary diary){
            return Response.builder()
                    .imageUrl(diary.getImageUrl())
                    .home(diary.getHome())
                    .away(diary.getAway())
                    .place(diary.getPlace())
                    .seat(diary.getSeat())
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .lineUp(diary.getLineUp())
                    .mvp(diary.getMvp())
                    .location(diary.getLocation())
                    .gameResult(diary.getGameResult())
                    .gameDate(diary.getGameDate())
                    .createdAt(diary.getCreatedAt())
                    .build();
        }

    }
}
