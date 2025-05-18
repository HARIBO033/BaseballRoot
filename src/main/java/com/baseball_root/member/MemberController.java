package com.baseball_root.member;

import com.baseball_root.friend.FriendService;
import com.baseball_root.global.response.CommonResponse;
import com.baseball_root.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @GetMapping("/members/{memberId}")
    public CommonResponse<?> getMember(@PathVariable(name = "memberId") Long memberId){
        MemberDto.Response member = memberService.getMember(memberId);
        log.info("getMember 호출 member: " + member);
        return CommonResponse.success(SuccessCode.REQUEST_SUCCESS, member);
    }

    @PostMapping("/members/login")
    public CommonResponse<MemberDto.Response> loginMember(@RequestBody MemberDto.LoginMemberRequest memberDto) {
        MemberDto.Response member = memberService.loginMember(memberDto);
        if (member == null) {
            return CommonResponse.success(SuccessCode.LOGIN_FAIL,null);
        }
        log.info("loginMember 호출 member = " + member);
        return CommonResponse.success(SuccessCode.LOGIN_SUCCESS, member);
    }
    //회원 추가
    @PostMapping(value = "/members/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<MemberDto.Response> saveMember(@RequestPart(value = "memberDto") MemberDto.Request memberDto,
                                                         @RequestPart(required = false, value = "file") MultipartFile file) {
        MemberDto.Response savedMember = memberService.saveMember(memberDto,file);
        log.info("saveMember 호출 savedMember = " + savedMember);
        return CommonResponse.success(SuccessCode.REGISTER_MEMBER_SUCCESS, savedMember);
    }

    //프로필 수정
    @PutMapping(value = "/members/{memberId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<MemberDto.Response> updateMemberInfo(@PathVariable(name = "memberId") Long memberId,
                                                               @RequestPart(value = "memberDto") MemberDto.UpdateMemberRequest memberDto,
                                                               @RequestPart(required = false, value = "file") MultipartFile file) {
        log.info("@@FIlE : {}", file);
        log.info("updateMemberInfo 호출 memberId = " + memberId);
        MemberDto.Response updatedMember = memberService.updateMember(memberId, memberDto, file);
        return CommonResponse.success(SuccessCode.REQUEST_SUCCESS, updatedMember);
    }

    //회원 탈퇴
    @DeleteMapping("/members/{memberId}")
    public CommonResponse<?> deleteMember(@PathVariable(name = "memberId") Long memberId) {
        log.info("deleteMember 호출 memberId = " + memberId);
        memberService.deleteMember(memberId);
        return CommonResponse.success(SuccessCode.DELETE_MEMBER_SUCCESS, null);
    }
}
