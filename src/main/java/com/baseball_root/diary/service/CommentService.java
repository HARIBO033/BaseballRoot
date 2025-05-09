package com.baseball_root.diary.service;

import com.baseball_root.Issue.Issue;
import com.baseball_root.Issue.IssueRepository;
import com.baseball_root.Issue.IssueType;
import com.baseball_root.Notification.NotificationService;
import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.dto.CommentDto;
import com.baseball_root.diary.repository.CommentRepository;
import com.baseball_root.diary.repository.DiaryRepository;
import com.baseball_root.global.exception.custom_exception.InvalidCommentIdException;
import com.baseball_root.global.exception.custom_exception.InvalidMemberIdException;
import com.baseball_root.global.exception.custom_exception.InvalidPostIdException;
import com.baseball_root.global.exception.custom_exception.NotFountParentCommentException;
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final IssueRepository issueRepository;
    private final NotificationService notificationService;

    public List<Comment> getComments(Long diaryId) {
        List<Comment> commentList = commentRepository.findAll();
        System.out.println(commentList);
        return commentList;
    }

    //댓글 생성
    @Transactional
    public void createComment(Long diaryId, CommentDto.Request commentDto) {

        Member author = validationMember(commentDto);
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(InvalidPostIdException::new);
        Comment comment = commentDto.toEntity(diary, author);

        if (commentDto.getParentCommentId() != null) {
            //부모댓글 찾기
            Comment parentComment = commentRepository.findById(commentDto.getParentCommentId())
                    .orElseThrow(NotFountParentCommentException::new);
            //부모댓글의 자식댓글로 저장
            comment.setParentComment(parentComment);
        }

        commentRepository.save(comment);
        if (!Objects.equals(diary.getMember().getId(), author.getId())) {
            if (commentDto.getParentCommentId() == null) {
                issueRepository.save(Issue.createIssue(author, diary.getMember(), IssueType.COMMENT));
                notificationService.send(String.valueOf(diary.getMember().getId()), author.getName() + "님이 회원님의 다이어리에 댓글을 남겼습니다.", IssueType.COMMENT, null);
            } else {
                issueRepository.save(Issue.createIssue(comment.getParent().getMember(), author, IssueType.REPLY));
                notificationService.send(String.valueOf(comment.getParent().getMember().getId()), author.getName() + "님이 회원님의 댓글에 답글을 남겼습니다.", IssueType.REPLY, null);
            }
        }

    }


    //다이어리 + 댓글 조회
    public List<CommentDto.Response> getCommentsByDiary(Long diaryId) {
        // 해당 다이어리가 있는지 확인
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(InvalidPostIdException::new);
        // 해당 다이어리의 댓글들을 가져옴
        List<Comment> comments = commentRepository.findByDiaryAndParentIsNull(diary);

        return comments.stream().map(CommentDto.Response::fromEntity).collect(Collectors.toList());
        //return commentRepository.findByDiaryIdAndParentIsNull(diary.getId());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(InvalidCommentIdException::new);
        commentRepository.delete(comment);
    }

    public Member validationMember(CommentDto.Request commentDto) {
        if (commentDto.getMemberId() == null) {
            throw new InvalidMemberIdException();
        }
        return memberRepository.findById(commentDto.getMemberId()).orElseThrow();
    }


}
