package com.baseball_root.Issue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IssueType {
    COMMENT("댓글"),
    DIARY_REACTION("다이어리 좋아요"),
    COMMENT_REACTION("댓글 좋아요"),
    FOLLOW_REQUEST("팔로우 요청"),
    FOLLOW_ACCEPTED("팔로우 수락");

    private final String description;
}
