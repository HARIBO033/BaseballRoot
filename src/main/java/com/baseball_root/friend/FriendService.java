package com.baseball_root.friend;

import com.baseball_root.member.Member;
import com.baseball_root.member.MemberDto;
import com.baseball_root.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final MemberRepository memberRepository;
    private final FriendManagementRepository friendManagementRepository;


    public List<FriendManagementDto> getFriendRequestedList(Long memberId) {
        List<FriendManagementDto> friendManagementList =
                friendManagementRepository.findByReceiverIdAndStatus_Requested(memberId)
                        .stream()
                        .map(FriendManagement::toDto)
                        .collect(Collectors.toList());

        return friendManagementList;
    }

    public void sendFriendRequest(Long senderId, Long receiverId) {
        Member sender = memberRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender id"));
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver id"));

        // 만약 이미 친구 요청을 받은 상태라면
        if (friendManagementRepository.findBySenderAndReceiverAndStatus_Requested(senderId, receiverId) != null) {
            throw new IllegalArgumentException("친구 요청 중입니다");
        }

        FriendManagement friendManagement = FriendManagement.builder()
                .sender(sender)
                .receiver(receiver)
                .status(FriendStatus.REQUESTED)
                .build();


        friendManagementRepository.save(friendManagement);
    }

    /*  */
    @Transactional
    public void acceptFriendRequest(Long senderId, Long receiverId) {
        FriendManagement friendManagement = friendManagementRepository
                .findBySenderAndReceiverAndStatus_Requested(senderId, receiverId);
        //sender와 receiver 둘 다 친구목록에 추가되어야함
        Member sender = memberRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender id"));
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver id"));

        sender.addFriend(receiver);
        receiver.addFriend(sender);
        friendManagement.setStatus(FriendStatus.ACCEPTED);

    }

    public void rejectFriendRequest(Long requestId) {
        friendManagementRepository.findById(requestId)
                .ifPresentOrElse(friendManagement -> {
                    friendManagement.setStatus(FriendStatus.REJECTED);
                    //friendManagementRepository.save(friendManagement);
                }, () -> {
                    throw new IllegalArgumentException("Invalid friend request id");
                });
    }

    public void deleteFriend(Long memberId, Long friendId) {

        FriendManagement friendManagement = friendManagementRepository
                .findBySenderAndReceiverAndStatus_Accepted(memberId, friendId);
        if (friendManagement == null) {
            throw new EntityNotFoundException("Friend relationship not found.");
        }
        Member member = memberRepository.findById(friendManagement.getSender().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id (M)"));
        Member friend = memberRepository.findById(friendManagement.getReceiver().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id (F)"));

        member.getFriends().remove(friend);
        friend.getFriends().remove(member);
    }

    public List<MemberDto> getFriendList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));

        return member.getFriends().stream()
                .map(Member::toDto)
                .collect(Collectors.toList());
    }
}
