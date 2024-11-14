package com.baseball_root.Issue;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueDto {


    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String senderNickname;
        private String receiverNickname;
        private IssueType issueType;
        private boolean isRead;

        public static IssueDto.Response fromEntity(Issue issue) {
            return IssueDto.Response.builder()
                    .id(issue.getId())
                    .senderNickname(issue.getSender().getNickname())
                    .receiverNickname(issue.getReceiver().getNickname())
                    .issueType(issue.getIssueType())
                    .isRead(issue.isRead())
                    .build();
        }
    }
}
