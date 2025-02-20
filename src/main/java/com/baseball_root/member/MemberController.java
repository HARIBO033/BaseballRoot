package com.baseball_root.member;

import com.baseball_root.friend.FriendService;
import com.baseball_root.global.response.CommonResponse;
import com.baseball_root.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final FriendService friendService;
    private final MemberService memberService;

    @GetMapping("/members/{memberId}/friends")
    public CommonResponse<List<MemberDto>> getFriends(@PathVariable(name = "memberId") Long memberId) {
        List<MemberDto> friends = friendService.getFriendList(memberId);
        log.info("getFriends 호출 friends = " + friends);
    return CommonResponse.success(SuccessCode.REQUEST_SUCCESS, friends);
    }

    //회원 추가
    @PostMapping("/members")
    public CommonResponse<MemberDto.Response> saveMember(@RequestBody MemberDto.Request memberDto) {
        MemberDto.Response savedMember = memberService.saveMember(memberDto);
        log.info("saveMember 호출 savedMember = " + savedMember);
        return CommonResponse.success(SuccessCode.REQUEST_SUCCESS, savedMember);
    }

    //프로필 수정
    @PutMapping("/members/{memberId}")
    public CommonResponse<MemberDto.Response> updateMemberInfo(@PathVariable(name = "memberId") Long memberId,
                                                               @RequestPart(value = "memberDto") MemberDto.Request memberDto,
                                                               @RequestPart(required = false, value = "file") MultipartFile file) {
        log.info("@@FIlE : {}", file);
        MemberDto.Response updatedMember = memberService.updateMember(memberId, memberDto, file);
        log.info("updateMemberInfo 호출 updatedMember = " + updatedMember);
        return CommonResponse.success(SuccessCode.REQUEST_SUCCESS, updatedMember);
    }
}
