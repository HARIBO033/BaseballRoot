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
}