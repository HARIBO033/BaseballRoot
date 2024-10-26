package com.baseball_root.friend;

import com.baseball_root.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FriendRequestDto {
    private Member sender;
    private Member receiver;
    private FriendStatus status;
}
