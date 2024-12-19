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

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private Long id;
        private String nickname;
        private String favoriteTeam;
        private String profileImage;

    }

    @Getter
    @Builder
    public static class Response{
        private Long id;
        private String nickname;
        private String favoriteTeam;
        private String memberCode;
        private String profileImage;

        public static Response fromEntity(Member member){
            return Response.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .favoriteTeam(member.getFavoriteTeam())
                    .memberCode(member.getMemberCode())
                    .profileImage(member.getProfileImage())
                    .build();
        }
        public static Response fromEntity(Member member, String profileImage){
            return Response.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .favoriteTeam(member.getFavoriteTeam())
                    .memberCode(member.getMemberCode())
                    .profileImage(profileImage)
                    .build();
        }
    }
}
