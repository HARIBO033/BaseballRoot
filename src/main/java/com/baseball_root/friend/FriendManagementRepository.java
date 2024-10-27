package com.baseball_root.friend;

import com.baseball_root.friend.FriendManagement;
import com.baseball_root.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendManagementRepository extends JpaRepository<FriendManagement, Long> {

    @Query("SELECT f FROM FriendManagement f " +
            "WHERE f.receiver.id=:receiverId AND f.status='REQUESTED'")
    List<FriendManagement> findByReceiverIdAndStatus_Requested(@Param("receiverId") Long receiverId);


    // sender와 receiver로 조회 결과가 있으면 REQUESTED 상태인지 확인
    @Query("SELECT f FROM FriendManagement f " +
            "WHERE f.sender.id=:senderId AND f.receiver.id=:receiverId AND f.status='REQUESTED'")
    FriendManagement findBySenderAndReceiverAndStatus_Requested(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    @Query("SELECT f FROM FriendManagement f " +
            "WHERE f.sender.id=:senderId AND f.receiver.id=:receiverId AND f.status='ACCEPTED'")
    FriendManagement findBySenderAndReceiverAndStatus_Accepted(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    Optional<FriendManagement> findByReceiverId(Long receiverId);
}
