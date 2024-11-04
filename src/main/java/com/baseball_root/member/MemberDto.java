package com.baseball_root.member;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberDto {
    private Long id;
    private String nickname;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private Long id;
        private String nickname;
        private String favoriteTeam;
    }

    @Getter
    @Builder
    public static class Response{
        private Long id;
        private String nickname;
        private String favoriteTeam;

        public static Response fromEntity(Member member){
            return Response.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .favoriteTeam(member.getFavoriteTeam())
                    .build();
        }
    }
}
