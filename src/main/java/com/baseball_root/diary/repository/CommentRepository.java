package com.baseball_root.diary.repository;

import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByDiaryIdAndParentIsNull(Long diaryId);

    List<Comment> findByDiaryAndParentIsNull(Diary diary);

    List<Comment> findAllByMember(Member member);
}
