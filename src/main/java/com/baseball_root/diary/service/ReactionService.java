package com.baseball_root.diary.service;

import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.dto.ReactionDto;
import com.baseball_root.diary.repository.CommentRepository;
import com.baseball_root.diary.repository.ReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final CommentRepository commentRepository;
    @Transactional
    public void saveCommentReaction(ReactionDto.Request reactionDto) {
        boolean requestReactionType = reactionDto.isReactionType();
        reactionRepository.save(reactionDto.toDto());

        Comment comment = commentRepository.findById(reactionDto.getCommentId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Comment not found for id: " + reactionDto.getCommentId()));
        comment.nullCheck();

        if (requestReactionType) {
            comment.increaseReactionCount();
        } else{
            comment.decreaseReactionCount();
        }

    }
}
