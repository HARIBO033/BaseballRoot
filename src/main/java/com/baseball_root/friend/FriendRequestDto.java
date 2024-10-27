package com.baseball_root.friend;

import com.baseball_root.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class FriendRequestDto {
    private Member sender;
    private Member receiver;
    private FriendStatus status;
}
