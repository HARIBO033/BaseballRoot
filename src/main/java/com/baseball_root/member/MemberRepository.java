package com.baseball_root.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Member findByNickname(String nickname);

    /*@Query("SELECT DISTINCT m FROM Member m JOIN FETCH m.friends WHERE m.id = :memberId")
    List<MemberDto> findFriendListById(@Param("memberId") Long memberId);*/
}
