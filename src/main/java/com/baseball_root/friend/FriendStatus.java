package com.baseball_root.friend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FriendStatus {
    REQUESTED("요청받음"), 
    ACCEPTED("수락됨"), 
    REJECTED("거부됨");

    private final String description;
}
