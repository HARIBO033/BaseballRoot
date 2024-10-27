package com.baseball_root.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    // 받은 친구 요청 리스트 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<FriendManagementDto>> getFriendRequestedList(@PathVariable("memberId") Long memberId) {

        return ResponseEntity.ok(friendService.getFriendRequestedList(memberId));
    }

    @GetMapping("/request/{senderId}/send/{receiverId}")
    public ResponseEntity<String> sendFriendRequest(@PathVariable(name = "senderId") Long senderId,
                                                    @PathVariable(name = "receiverId") Long receiverId) {
        System.out.println("친구추가");
        System.out.println("@@@@@@" + senderId+ " " + receiverId);
        friendService.sendFriendRequest(senderId, receiverId);
        return ResponseEntity.ok(senderId + "번 유저가 " + receiverId + "번 유저에게 " + "친구 추가 요청");
    }

    // 친구 요청 수락 or 거절
    @PutMapping("/request/{senderId}/response/{receiverId}")
    public ResponseEntity<String> respondToFriendRequest(@PathVariable(name = "senderId") Long senderId,
                                                         @PathVariable(name = "receiverId") Long receiverId,
                                                         @RequestParam(name = "action") String action) {
        // 수락 혹은 거절 처리 로직
        if (action.equals("ACCEPTED")) {
            friendService.acceptFriendRequest(senderId, receiverId);
            return ResponseEntity.ok("친구 요청 수락됨");
        } else if (action.equals("REJECTED")) {
            friendService.rejectFriendRequest(senderId);
            return ResponseEntity.ok("친구 요청 거부됨");
        }
        return ResponseEntity.badRequest().body("Invalid action");
    }

    @DeleteMapping("/request/{requestId}/delete/{friendId}")
    public ResponseEntity<String> deleteFriendRequest(@PathVariable(name = "requestId") Long requestId,
                                                      @PathVariable(name = "friendId") Long friendId) {
        friendService.deleteFriend(requestId,friendId);
        return ResponseEntity.ok("친구 삭제 완료");
    }
}
