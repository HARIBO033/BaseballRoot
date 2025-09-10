package com.baseball_root.diary.service;

import com.baseball_root.Issue.Issue;
import com.baseball_root.Issue.IssueRepository;
import com.baseball_root.Issue.IssueType;
import com.baseball_root.Notification.NotificationService;
import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.domain.Reaction;
import com.baseball_root.diary.dto.ReactionDto;
import com.baseball_root.diary.repository.CommentRepository;
import com.baseball_root.diary.repository.DiaryRepository;
import com.baseball_root.diary.repository.ReactionRepository;
import com.baseball_root.global.exception.custom_exception.CannotLikeOwnPostOrCommentException;
import com.baseball_root.global.exception.custom_exception.InvalidCommentIdException;
import com.baseball_root.global.exception.custom_exception.InvalidMemberIdException;
import com.baseball_root.global.exception.custom_exception.InvalidPostIdException;
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final NotificationService notificationService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public boolean saveDiaryOrCommentReaction(ReactionDto.Request reactionDto) {
        boolean requestReactionType = reactionDto.isReactionType();
        // 댓글에 대한 반응인지 다이어리에 대한 반응인지 확인
        Diary diary = diaryRepository.findById(reactionDto.getDiaryId())
                .orElseThrow(InvalidPostIdException::new);
        if (reactionDto.getCommentId() == null || reactionDto.getCommentId() == 0L) {
            diary.nullCheck();

            Member sender = memberRepository.findById(reactionDto.getMemberId())
                    .orElseThrow(InvalidMemberIdException::new);

            Member receiver = diary.getMember();

            if (sender.getId().equals(receiver.getId())) {
                throw new CannotLikeOwnPostOrCommentException();
            }
            Optional<Reaction> existing = reactionRepository.findByMemberAndDiaryAndCommentIsNull(sender, diary);

            String redisKey = "diary:" + diary.getId() + ":reactions";

            if (existing.isPresent()) {
                //좋아요 취소
                reactionRepository.delete(existing.get());
                //diary.decreaseReactionCount();
                // Redis에서 해당 회원의 좋아요 상태 제거
                redisTemplate.opsForValue().decrement(redisKey);
                return false;
            } else {
                //좋아요 추가
                Reaction newReaction = reactionDto.toEntity(diary, sender, null, true);
                reactionRepository.save(newReaction);
                //diary.increaseReactionCount();
                // Redis에 해당 회원의 좋아요 상태 추가
                redisTemplate.opsForValue().increment(redisKey);

                issueRepository.save(Issue.createIssue(sender, receiver, IssueType.DIARY_REACTION));
                notificationService.send(
                        String.valueOf(receiver.getId()),
                        sender.getName() + "님이 회원님의 다이어리에 좋아요를 눌렀습니다.",
                        IssueType.COMMENT,
                        null
                );
                return true;
            }
        } else {
            Comment comment = commentRepository.findById(reactionDto.getCommentId())
                    .orElseThrow(InvalidCommentIdException::new);
            comment.nullCheck();

            Member sender = memberRepository.findById(reactionDto.getMemberId())
                    .orElseThrow(InvalidMemberIdException::new);

            Member receiver = comment.getMember();

            if (sender.getId().equals(receiver.getId())) {
                throw new CannotLikeOwnPostOrCommentException();
            }

            Optional<Reaction> existing = reactionRepository.findByMemberAndComment(sender, comment);

            String redisKey = "comment:" + comment.getId() + ":reactions";
            if (existing.isPresent()) {
                //좋아요 취소
                reactionRepository.delete(existing.get());
                //comment.decreaseReactionCount();
                // Redis에서 해당 회원의 좋아요 상태 제거
                redisTemplate.opsForValue().decrement(redisKey);
                return false;
            } else {
                //좋아요 추가
                Reaction newReaction = reactionDto.toEntity(diary, sender, comment, true);
                reactionRepository.save(newReaction);
                //comment.increaseReactionCount();
                redisTemplate.opsForValue().increment(redisKey);

                // 알림 및 이슈 저장
                if (comment.getParent() == null) {
                    // 일반 댓글
                    issueRepository.save(Issue.createIssue(sender, receiver, IssueType.COMMENT_REACTION));
                    notificationService.send(
                            String.valueOf(receiver.getId()),
                            sender.getName() + "님이 회원님의 댓글에 좋아요를 눌렀습니다.",
                            IssueType.COMMENT,
                            null
                    );
                } else {
                    // 대댓글
                    issueRepository.save(Issue.createIssue(sender, receiver, IssueType.REPLY_REACTION));
                    notificationService.send(
                            String.valueOf(receiver.getId()),
                            sender.getName() + "님이 회원님의 대댓글에 좋아요를 눌렀습니다.",
                            IssueType.REPLY,
                            null
                    );
                }
                return true;
            }
        }
    }

    public List<ReactionDto.Response> getDiaryAndCommentReaction(Long postId, Long memberId) {
        Optional<List<Reaction>> reactionList = reactionRepository.findByMemberIdAndDiaryIdAndReactionTypeTrue(memberId, postId);

        return reactionList.stream()
                .flatMap(List::stream)
                .map(ReactionDto.Response::fromEntity)
                .collect(Collectors.toList());
    }
}
