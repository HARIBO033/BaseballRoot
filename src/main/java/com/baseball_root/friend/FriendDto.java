package com.baseball_root.friend;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FriendDto {
    private Long id;
    private Long memberId;
    private Long friendId;
    private String nickname;


    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Response {
        private Long id;
        private Long friendId;
    }


}
