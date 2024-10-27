package com.baseball_root.member;

import com.baseball_root.friend.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final FriendService friendService;

    @GetMapping("/members/{memberId}/friends")
    public ResponseEntity<List<MemberDto>> getFriends(@PathVariable(name = "memberId") Long memberId) {
        List<MemberDto> friends = friendService.getFriendList(memberId);
        return ResponseEntity.ok(friends);
    }
}
