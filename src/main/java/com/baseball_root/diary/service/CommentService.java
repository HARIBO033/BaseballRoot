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
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+commentDto.getMemberId().toString());
        Member author = validationMember(commentDto);
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new IllegalArgumentException("다이어리 ID를 찾을 수 없습니다: " + diaryId)
        );
        Comment comment = commentDto.toEntity(diary, author);

        if (commentDto.getParentCommentId() != null) {
            //부모댓글 찾기
            Comment parentComment = commentRepository.findById(commentDto.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 없습니다."));
            //부모댓글의 자식댓글로 저장
            comment.setParentComment(parentComment);
        }

        commentRepository.save(comment);
        if (!Objects.equals(diary.getMember().getId(), author.getId())){
            issueRepository.save(Issue.createIssue(diary.getMember(), author, IssueType.COMMENT));
            notificationService.send(String.valueOf(diary.getMember().getId()), author.getName() + "님이 회원님의 다이어리에 댓글을 남겼습니다.",IssueType.COMMENT, null);
        }

    }


    //다이어리 + 댓글 조회
    public List<Comment> getCommentsByDiary(Long diaryId) {
        // 해당 다이어리가 있는지 확인
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("다이어리 ID를 찾을 수 없습니다: " + diaryId));
        // 해당 다이어리의 댓글들을 가져옴
        return commentRepository.findByDiaryAndParentIsNull(diary);
        //return commentRepository.findByDiaryIdAndParentIsNull(diary.getId());
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        commentRepository.delete(comment);
    }

    public Member validationMember(CommentDto.Request commentDto) {
        if (commentDto.getMemberId() == null) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }
        return memberRepository.findById(commentDto.getMemberId()).orElseThrow();
    }
}
