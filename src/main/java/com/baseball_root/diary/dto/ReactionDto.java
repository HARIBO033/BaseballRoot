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
        private long commentId;
        private long memberId;
        private boolean reactionType;

        public Reaction toDto(){
            return Reaction.builder()
                    .commentId(commentId)
                    .memberId(memberId)
                    .reactionType(reactionType)
                    .build();
        }
    }
}
