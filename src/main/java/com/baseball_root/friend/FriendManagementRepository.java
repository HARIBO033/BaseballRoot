package com.baseball_root.friend;

import com.baseball_root.friend.FriendManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendManagementRepository extends JpaRepository<FriendManagement, Long> {

    @Query("SELECT f FROM FriendManagement f " +
            "WHERE f.receiver.id=:receiverId AND f.status='REQUESTED'")
    List<FriendManagement> findByReceiverIdAndStatus_Requested(Long receiverId);
}