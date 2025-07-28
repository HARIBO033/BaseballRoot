package com.baseball_root.diary.repository;

import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.domain.Reaction;
import com.baseball_root.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {


    Optional<Reaction> findByMemberAndDiaryAndCommentIsNull(Member member, Diary diary);

    boolean existsByMemberAndDiaryAndCommentIsNull(Member member, Diary diary);

    Optional<Reaction> findByMemberAndCommentAndDiaryIsNull(Member member, Comment comment);

    boolean existsByMemberAndCommentAndDiaryIsNull(Member member, Comment comment);

    Optional<List<Reaction>> findByMemberIdAndDiaryIdAndReactionTypeTrue(Long memberId, Long postId);
}
