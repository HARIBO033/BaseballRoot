package com.baseball_root.Issue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class IssueDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String senderNickname;
        private String receiverNickname;
        private IssueType issueType;
        private boolean isRead;
        private String timeAgo; // 상대적 시간 추가

        public static IssueDto.Response fromEntity(Issue issue) {
            return new Response(
                    issue.getId(),
                    issue.getSender().getNickname(),
                    issue.getReceiver().getNickname(),
                    issue.getIssueType(),
                    issue.isRead(),
                    getTimeAgo(issue.getCreatedAt())
            );
        }

        private static String getTimeAgo(LocalDateTime createdAt) {
            Duration duration = Duration.between(createdAt, LocalDateTime.now());
            long seconds = duration.getSeconds();
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            if (seconds < 60) return "방금 전";
            if (minutes < 60) return minutes + "분 전";
            if (hours < 24) return hours + "시간 전";
            return days + "일 전";
        }

    }
}
