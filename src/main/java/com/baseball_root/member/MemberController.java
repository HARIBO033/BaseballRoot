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
    private final MemberService memberService;

    @GetMapping("/members/{memberId}/friends")
    public ResponseEntity<List<MemberDto>> getFriends(@PathVariable(name = "memberId") Long memberId) {
        List<MemberDto> friends = friendService.getFriendList(memberId);
        return ResponseEntity.ok(friends);
    }

    //프로필 수정
    @PutMapping("/members/{memberId}")
    public ResponseEntity<MemberDto.Response> updateMember(@PathVariable(name = "memberId") Long memberId, @RequestBody MemberDto.Request memberDto) {
        MemberDto.Response updatedMember = memberService.updateMember(memberId, memberDto);
        return ResponseEntity.ok(updatedMember);
    }
}
