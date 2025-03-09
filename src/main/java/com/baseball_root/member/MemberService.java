package com.baseball_root.member;

import com.baseball_root.global.S3Service;
import com.baseball_root.global.exception.custom_exception.InvalidMemberIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Transactional
    public MemberDto.Response updateMember(Long memberId, MemberDto.Request memberDto, MultipartFile file){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(InvalidMemberIdException::new);
        if (!member.getProfileImage().isEmpty()){
            s3Service.deleteFile(member.getProfileImage());
        }
        String key = s3Service.uploadFile(file);
        member.update(key, memberDto.getNickname(), memberDto.getFavoriteTeam());
        String imageUrl = s3Service.getFileUrl(key);
        return MemberDto.Response.fromEntity(member, imageUrl);
    }

    @Transactional
    public MemberDto.Response saveMember(MemberDto.Request memberDto) {
        CreateUuid createUuid = new CreateUuid();
        Member member = createMemberEntity(memberDto, createUuid);
        memberRepository.save(member);
        return MemberDto.Response.fromEntity(member);
    }

    private Member createMemberEntity(MemberDto.Request memberDto, CreateUuid createUuid) {
        return Member.builder()
                .nickname(memberDto.getNickname())
                .profileImage(memberDto.getProfileImage())
                .favoriteTeam(memberDto.getFavoriteTeam())
                .memberCode(createUuid.createUuid()) //TODO : uuid 객체생성방식 변경
                .age(memberDto.getAge())
                .name(memberDto.getName())
                .gender(memberDto.getGender())
                .build();
    }

    public MemberDto.Response getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(InvalidMemberIdException::new);
        return MemberDto.Response.fromEntity(member);
    }
}