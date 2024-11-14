package com.baseball_root.diary.dto;

import com.baseball_root.diary.domain.Diary;
import com.baseball_root.member.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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

        @Length(max = 500, message = "내용은 500자리를 넘을 수 없습니다.")
        @NotBlank(message = "내용은 필수 입력값입니다.")
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
        private String createdAt;

        private Response(String imageUrl, String home, String away, String place, String seat, String title, String content, String lineUp, String mvp, String location, String gameResult, String gameDate, String nickname, String createdAt) {
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
                    diary.getFormattedCreatedAt());
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
                    .nickname(diary.getMember().getNickname())
                    .createdAt(diary.getFormattedCreatedAt())
                    .build();
        }

    }
}
