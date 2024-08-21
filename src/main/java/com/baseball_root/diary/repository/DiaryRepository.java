package com.baseball_root.diary.repository;

import com.baseball_root.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    //List<Diary> findByMemberIdAndDate(Long memberId, String date);
}
