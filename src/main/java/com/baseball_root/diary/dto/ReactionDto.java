package com.baseball_root.diary.dto;

import com.baseball_root.diary.domain.Reaction;
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

        public Reaction toEntity(){
            return Reaction.builder()
                    .diaryId(diaryId)
                    .commentId(commentId)
                    .memberId(memberId)
                    .reactionType(reactionType)
                    .build();
        }
    }
}
