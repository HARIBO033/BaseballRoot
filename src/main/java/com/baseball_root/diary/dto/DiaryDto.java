package com.baseball_root.diary.dto;

import com.baseball_root.attach.AttachImage;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.member.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Builder
public class DiaryDto {



    @Getter
    @Setter
    public static class Request{
        @JsonProperty("home")
        private String home;

        @JsonProperty("away")
        private String away;

        @NotBlank
        @JsonProperty("place")
        private String place;

        @JsonProperty("seat")
        private String seat;

        @NotBlank
        @JsonProperty("title")
        private String title;

        @Length(max = 500, message = "내용은 500자리를 넘을 수 없습니다.")
        @NotBlank(message = "내용은 필수 입력값입니다.")
        @JsonProperty("content")
        private String content;

        @JsonProperty("lineUp")
        private String lineUp;

        @JsonProperty("mvp")
        private String mvp;

        @JsonProperty("location")
        private String location;

        @JsonProperty("gameResult")
        private String gameResult;

        @JsonProperty("gameDate")
        private String gameDate;


        public Diary toEntity(String home, String away,String place, String seat, String title, String content, String lineUp, String mvp, Member member){
            return new Diary(home,away, place, seat, title, content, lineUp, mvp, member);
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class Response{
        private Long id;
        private List<String> attachImagesUrl;
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
        private Long reactionCount;
        private String createdAt;

        private Response(Long id, String home, String away, String place, String seat, String title, String content, String lineUp, String mvp, String location, String gameResult, String gameDate, String nickname, String createdAt) {
            this.id = id;
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
                    diary.getId(),
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

        public static Response fromEntity(Diary diary, List<String> attachImagesUrl){
            return Response.builder()
                    .attachImagesUrl(attachImagesUrl)
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
                    .reactionCount(diary.getReactionCount())
                    .createdAt(diary.getFormattedCreatedAt())
                    .build();
        }

    }
}
