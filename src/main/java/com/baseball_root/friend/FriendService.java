package com.baseball_root.friend;

import com.baseball_root.member.Member;
import com.baseball_root.member.MemberDto;
import com.baseball_root.member.MemberRepository;
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


    public List<FriendManagement> getFriendRequestedList(Long memberId){

        return friendManagementRepository.findByReceiverIdAndStatus_Requested(memberId);
    }
    public void sendFriendRequest(FriendRequestDto friendRequestDto) {
        FriendManagement friendManagement = FriendManagement.builder()
                .sender(friendRequestDto.getSender())
                .receiver(friendRequestDto.getReceiver())
                .status(FriendStatus.REQUESTED)
                .build();
        friendManagementRepository.save(friendManagement);
    }

    /*  */
    @Transactional
    public void acceptFriendRequest(Long requestId) {
        Member sender = memberRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
        FriendManagement entity = friendManagementRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Invalid friend request id"));
        Member receiver = entity.getReceiver();
        //친추요청아이디로 친구요청을 찾음
        friendManagementRepository.findById(requestId)
                //친구요청이 있으면
                .ifPresentOrElse(friendManagement -> {
                    //친구요청의 상태를 수락으로 변경
                    friendManagement.setStatus(FriendStatus.ACCEPTED);
                    //친구요청을 저장
                    //friendManagementRepository.save(friendManagement);
                    //멤버의 friends에 친구요청을 보낸 사람을 추가
                    sender.addFriend(friendManagement.getSender());
                    receiver.addFriend(friendManagement.getReceiver());
                    // 해당 요청 삭제
                    //friendManagementRepository.deleteById(requestId);
                }, () -> {
                    throw new IllegalArgumentException("Invalid friend request id");
                });

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

    public void deleteFriend(Long memberId) {
        FriendManagement friendManagement = friendManagementRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member id"));

        Member member = memberRepository.findById(friendManagement.getSender().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
        Member friend = memberRepository.findById(friendManagement.getReceiver().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
        member.getFriends().remove(friend);
        friend.getFriends().remove(member);
    }

    public List<MemberDto> getFriends(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
        return member.getFriends().stream().map(Member::toDto).collect(Collectors.toList());
    }
}
