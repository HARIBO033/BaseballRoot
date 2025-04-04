package com.baseball_root.diary.service;

import com.baseball_root.Issue.Issue;
import com.baseball_root.Issue.IssueRepository;
import com.baseball_root.Issue.IssueType;
import com.baseball_root.Notification.NotificationService;
import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.dto.ReactionDto;
import com.baseball_root.diary.repository.CommentRepository;
import com.baseball_root.diary.repository.DiaryRepository;
import com.baseball_root.diary.repository.ReactionRepository;
import com.baseball_root.global.exception.custom_exception.InvalidCommentIdException;
import com.baseball_root.global.exception.custom_exception.InvalidMemberIdException;
import com.baseball_root.global.exception.custom_exception.InvalidPostIdException;
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
    private final DiaryRepository diaryRepository;
    private final NotificationService notificationService;

    @Transactional
    public void saveDiaryOrCommentReaction(ReactionDto.Request reactionDto) {
        boolean requestReactionType = reactionDto.isReactionType();
        // 댓글에 대한 반응인지 다이어리에 대한 반응인지 확인
        if (reactionDto.getDiaryId() != null && reactionDto.getCommentId() == null) {
            Diary diary = diaryRepository.findById(reactionDto.getDiaryId())
                    .orElseThrow(InvalidPostIdException::new);
            diary.nullCheck();
            if (requestReactionType) {
                diary.increaseReactionCount();
            } else {
                diary.decreaseReactionCount();
            }
            Member sender = memberRepository.findById(reactionDto.getMemberId())
                    .orElseThrow(InvalidMemberIdException::new);

            Member receiver = memberRepository.findById(diary.getMember().getId())
                    .orElseThrow(InvalidMemberIdException::new);

            issueRepository.save(Issue.createIssue(sender, receiver, IssueType.DIARY_REACTION));
            notificationService.send(String.valueOf(diary.getMember().getId()), sender.getName() + "님이 회원님의 다이어리에 좋아요를 눌렀습니다.", IssueType.COMMENT, null);
        } else {
            Comment comment = commentRepository.findById(reactionDto.getCommentId())
                    .orElseThrow(InvalidCommentIdException::new);
            comment.nullCheck();
            if (requestReactionType) {
                comment.increaseReactionCount();
            } else {
                comment.decreaseReactionCount();
            }
            Member sender = memberRepository.findById(reactionDto.getMemberId())
                    .orElseThrow(InvalidMemberIdException::new);

            Member receiver = memberRepository.findById(comment.getMember().getId())
                    .orElseThrow(InvalidMemberIdException::new);
            if (comment.getParent() == null) {
                issueRepository.save(Issue.createIssue(sender, receiver, IssueType.COMMENT_REACTION));
                notificationService.send(String.valueOf(receiver.getId()), sender.getName() + "님이 회원님의 댓글에 좋아요를 눌렀습니다.", IssueType.COMMENT, null);
            } else {
                issueRepository.save(Issue.createIssue(sender, receiver, IssueType.REPLY_REACTION));
                notificationService.send(String.valueOf(receiver.getId()), sender.getName() + "님이 회원님의 대댓글에 좋아요를 눌렀습니다.", IssueType.REPLY, null);
            }
        }
        reactionRepository.save(reactionDto.toEntity());
    }

}
