package com.baseball_root.diary.dto;

import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.member.Member;
import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;

public class CommentDto {
    @Getter
    @Setter
    @ToString
    public static class Request {
        private String content;
        private Long memberId;
        @Nullable
        private Long parentCommentId;

        public Comment toEntity(Diary diary, Member member) {
            return Comment.builder()
                    .content(content)
                    .member(member)
                    .diary(diary)
                    .build();
        }


    }

    @Getter
    @Setter
    @ToString
    public static class Response {
        private Long memberId;
        private String nickname;
        private Long diaryId;
        private Long commentId;
        private String content;
        private Long parentId;
        private List<CommentDto.Response> children;

        private boolean reactionType;

        private Long reactionCount;
        private String createdAt;

        public Response(Comment comment) {
            this.memberId = comment.getMember().getId();
            this.nickname = comment.getMember().getNickname();
            this.diaryId = comment.getDiary().getId();
            this.commentId = comment.getId();
            this.content = comment.getContent();
            if (comment.getParent() != null) {
                this.parentId = comment.getParent().getId();
            }
            if (!comment.getChildren().isEmpty())
                this.children = CommentDto.Response.toDto(comment.getChildren());
            this.reactionCount = comment.getReactionCount();
            this.createdAt = comment.getFormattedCreatedAt();
        }

        private static List<Response> toDto(List<Comment> children) {
            return children.stream()
                    .map(Response::fromEntity)
                    .toList();
        }

        public static Response fromEntity(Comment comment) {
            return new Response(comment);
        }
    }
}
