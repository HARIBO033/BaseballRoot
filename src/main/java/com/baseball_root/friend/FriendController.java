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

    @GetMapping("/{memberId}")
    public ResponseEntity<List<FriendManagement>> getFriendRequestedList(@PathVariable Long memberId) {

        return ResponseEntity.ok(friendService.getFriendRequestedList(memberId));
    }

    @PostMapping("/request")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        System.out.println("친구추가");
        friendService.sendFriendRequest(friendRequestDto);
        return ResponseEntity.ok("친구 추가 요청");
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<String> respondToFriendRequest(@PathVariable Long requestId, @RequestParam String action) {
        // 수락 혹은 거절 처리 로직
        if (action.equals("ACCEPTED")) {
            friendService.acceptFriendRequest(requestId);
            return ResponseEntity.ok("친구 요청 수락됨");
        } else if (action.equals("REJECTED")) {
            friendService.rejectFriendRequest(requestId);
            return ResponseEntity.ok("친구 요청 거부됨");
        }
        return ResponseEntity.badRequest().body("Invalid action");
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<String> deleteFriendRequest(@PathVariable Long requestId) {
        friendService.deleteFriend(requestId);
        return ResponseEntity.ok("친구 삭제 완료");
    }
}
