package com.baseball_root.friend;

import com.baseball_root.Issue.Issue;
import com.baseball_root.Issue.IssueRepository;
import com.baseball_root.Issue.IssueType;
import com.baseball_root.Notification.NotificationService;
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
    private final IssueRepository issueRepository;
    private final NotificationService notificationService;

    public List<FriendManagementDto> getFriendRequestedList(Long memberId) {
        List<FriendManagementDto> friendManagementList =
                friendManagementRepository.findByReceiverIdAndStatus_Requested(memberId)
                        .stream()
                        .map(FriendManagement::toDto)
                        .collect(Collectors.toList());

        return friendManagementList;
    }

    public void sendFriendRequest(Long senderId, String memberCode) {
        Member sender = memberRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender id"));
        Member codeMatchingFriend = memberRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member code"));

        // 만약 이미 친구 요청을 받은 상태라면
        if (friendManagementRepository
                .findBySenderAndReceiverAndStatus_Requested(senderId, codeMatchingFriend.getId()).isPresent()) {
            throw new IllegalArgumentException("친구 요청 중입니다");
        }

        FriendManagement friendManagement = FriendManagement.builder()
                .sender(sender)
                .receiver(codeMatchingFriend)
                .status(FriendStatus.REQUESTED)
                .build();

        issueRepository.save(Issue.createIssue(sender, codeMatchingFriend, IssueType.FOLLOW_REQUEST));
        friendManagementRepository.save(friendManagement);
        notificationService.send(String.valueOf(codeMatchingFriend.getId()), sender.getName() + "님이 친구 요청을 보냈습니다.", IssueType.FOLLOW_REQUEST, null);
    }

    /*  */
    @Transactional
    public void acceptFriendRequest(Long senderId, Long receiverId) {
        FriendManagement friendManagement = friendManagementRepository
                .findBySenderAndReceiverAndStatus_Requested(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender id"));
        //sender와 receiver 둘 다 친구목록에 추가되어야함
        Member sender = memberRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender id"));
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver id"));

        sender.addFriend(receiver);
        receiver.addFriend(sender);
        friendManagement.setStatus(FriendStatus.ACCEPTED);

        issueRepository.save(Issue.createIssue(sender, receiver, IssueType.FOLLOW_ACCEPTED));
        notificationService.send(String.valueOf(sender.getId()), receiver.getName() + "님이 친구 요청을 수락했습니다.", IssueType.FOLLOW_ACCEPTED, null);
    }

    @Transactional
    public void rejectFriendRequest(Long senderId, Long receiverId) {
        System.out.println("@@@@@@@@@@@@@@@@@@@" + senderId + " " + receiverId);
        FriendManagement friendManagement = friendManagementRepository
                .findBySenderAndReceiverAndStatus_Requested(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender id"));
        friendManagement.setStatus(FriendStatus.REJECTED);
    }

    @Transactional
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
