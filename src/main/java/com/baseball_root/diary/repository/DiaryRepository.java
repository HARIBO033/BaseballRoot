package com.baseball_root.diary.repository;

import com.baseball_root.diary.domain.Diary;
import com.baseball_root.member.Member;
import com.google.common.base.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d WHERE d.location = :location and d.member.id = :memberId and year (d.createdAt) = :season")
    List<Diary> findByLocationAndMemberIdAndCreatedAt(@Param("location") String location, @Param("memberId") Long memberId, @Param("season") String season);

}
