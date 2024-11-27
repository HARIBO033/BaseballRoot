package com.baseball_root.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto.Response updateMember(Long memberId, MemberDto.Request memberDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + memberId));
        member.update(memberDto.getNickname(), memberDto.getFavoriteTeam());
        return MemberDto.Response.fromEntity(member);
    }

    @Transactional
    public MemberDto.Response saveMember(MemberDto.Request memberDto) {
        CreateUuid createUuid = new CreateUuid();
        Member member = Member.builder()
                .nickname(memberDto.getNickname())
                .profileImage(memberDto.getProfileImage())
                .favoriteTeam(memberDto.getFavoriteTeam())
                .memberCode(createUuid.createUuid())
                .build();
        memberRepository.save(member);
        return MemberDto.Response.fromEntity(member);
    }
}