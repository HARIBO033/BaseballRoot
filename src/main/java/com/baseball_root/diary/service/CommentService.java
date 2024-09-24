package com.baseball_root.diary.service;

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

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    public List<Comment> getComments(Long diaryId) {
        List<Comment> commentList = commentRepository.findAll();
        System.out.println(commentList.toString());
        return commentList;
    }
    @Transactional
    public void createComment(Long diaryId, CommentDto.Request commentDto) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+commentDto.getMemberId().toString());
        Member author = validationMember(commentDto);
        Diary diary = diaryRepository.findById(diaryId).orElseThrow();
        Comment comment = commentDto.toEntity(diary, author);

        if (commentDto.getParentCommentId() == null) {
            commentRepository.save(comment);
        } else {
            //부모댓글 찾기
            Comment parentComment = commentRepository.findById(commentDto.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 없습니다."));
            //부모댓글의 자식댓글로 저장
            comment.setParentComment(parentComment);
            commentRepository.save(comment);
        }
    }


    //다이어리 + 댓글 조회
    public List<Comment> getCommentsByDiary(Long diaryId) {
        // 해당 다이어리가 있는지 확인
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("Diary not found with id: " + diaryId));
        // 해당 다이어리의 댓글들을 가져옴
        return commentRepository.findByDiaryAndParentIsNull(diary);
        //return commentRepository.findByDiaryIdAndParentIsNull(diary.getId());
    }
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        commentRepository.delete(comment);
    }

    public Member validationMember(CommentDto.Request commentDto) {
        if (commentDto.getMemberId() == null) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }
        Member author = memberRepository.findById(commentDto.getMemberId()).orElseThrow();
        return author;
    }
}
