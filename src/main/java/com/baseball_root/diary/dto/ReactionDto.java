package com.baseball_root.diary.dto;

import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.domain.Reaction;
import com.baseball_root.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ReactionDto {

    @Getter
    @Setter
    @ToString
    public static class Request{
        private Long diaryId;
        private Long commentId;
        private Long memberId;
        private boolean reactionType;

        public Reaction toEntity(Diary diary, Member member, Comment comment, boolean reactionType) {
            return Reaction.builder()
                    .diary(diary)
                    .member(member)
                    .comment(comment)
                    .reactionType(reactionType)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Response {
        private Long diaryId;
        private Long commentId;
        private Long memberId;
        private boolean reactionType;

        public static Response fromEntity(Reaction reaction) {
            return Response.builder()
                    .diaryId(reaction.getDiary().getId())
                    .commentId(reaction.getComment() != null ? reaction.getComment().getId() : null)
                    .memberId(reaction.getMember().getId())
                    .reactionType(reaction.isReactionType())
                    .build();
        }
    }
}
