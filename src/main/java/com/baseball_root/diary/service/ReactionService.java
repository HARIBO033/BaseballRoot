package com.baseball_root.diary.service;

import com.baseball_root.Issue.Issue;
import com.baseball_root.Issue.IssueRepository;
import com.baseball_root.Issue.IssueType;
import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.dto.ReactionDto;
import com.baseball_root.diary.repository.CommentRepository;
import com.baseball_root.diary.repository.ReactionRepository;
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final MemberRepository memberRepository;

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
        } else {
            comment.decreaseReactionCount();
        }

        Member sender = memberRepository.findById(reactionDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender id"));

        Member receiver = memberRepository.findById(comment.getMember().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver id"));
        Issue issue = Issue.builder()
                .sender(sender)
                .receiver(receiver)
                .issueType(IssueType.REACTION)
                .isRead(false)
                .build();
        issueRepository.save(issue);

    }
}
