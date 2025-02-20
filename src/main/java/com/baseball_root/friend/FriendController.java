package com.baseball_root.friend;

import com.baseball_root.global.response.CommonResponse;
import com.baseball_root.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
@Slf4j
public class FriendController {

    private final FriendService friendService;

    // 받은 친구 요청 리스트 조회
    @GetMapping("/{memberId}")
    public CommonResponse<List<FriendManagementDto>> getFriendRequestedList(@PathVariable("memberId") Long memberId) {
        log.info("getFriendRequestedList 호출 memberId = " + memberId);
        return CommonResponse.success(SuccessCode.GET_FRIENDS_REQUEST_SUCCESS,friendService.getFriendRequestedList(memberId));
    }

    @GetMapping("/request/{senderId}/send")
    public CommonResponse<?> sendFriendRequest(@PathVariable(name = "senderId") Long senderId,
                                                    @RequestParam(name = "memberCode") String memberCode) {

        friendService.sendFriendRequest(senderId, memberCode);
        log.info("sendFriendRequest 호출 senderId = " + senderId + " memberCode = " + memberCode);
        return CommonResponse.success(SuccessCode.SEND_FRIEND_REQUEST_SUCCESS);
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
            friendService.rejectFriendRequest(senderId, receiverId);
            return ResponseEntity.ok("친구 요청 거부됨");
        }
        log.info("respondToFriendRequest 호출 senderId = " + senderId + " receiverId = " + receiverId + " action = " + action);
        return ResponseEntity.badRequest().body("Invalid action");
    }

    @DeleteMapping("/request/{requestId}/delete/{friendId}")
    public ResponseEntity<String> deleteFriendRequest(@PathVariable(name = "requestId") Long requestId,
                                                      @PathVariable(name = "friendId") Long friendId) {
        friendService.deleteFriend(requestId,friendId);
        log.info("deleteFriendRequest 호출 requestId = " + requestId + " friendId = " + friendId);
        return ResponseEntity.ok("친구 삭제 완료");
    }
}
