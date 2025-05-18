package com.baseball_root.member;

import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.repository.CommentRepository;
import com.baseball_root.diary.repository.DiaryRepository;
import com.baseball_root.friend.FriendManagementRepository;
import com.baseball_root.global.S3Service;
import com.baseball_root.global.exception.custom_exception.AlreadyExistsMemberException;
import com.baseball_root.global.exception.custom_exception.InvalidMemberIdException;
import com.baseball_root.global.exception.custom_exception.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {
    private final FriendManagementRepository friendManagementRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final CommentRepository commentRepository;
    private final S3Service s3Service;

    //
    public MemberDto.Response loginMember(MemberDto.LoginMemberRequest memberDto) {
        return memberRepository.findByNaverId(memberDto.getNaverId())
                .map(MemberDto.Response::fromEntity)
                .orElse(null);

        /*return memberRepository.findByNaverId(memberDto.getNaverId())
                .map(MemberDto.Response::fromEntity)
                .orElse(null);*/
    }

    @Transactional
    public MemberDto.Response saveMember(MemberDto.Request memberDto, MultipartFile file) {
        validateDuplicateNaverId(memberDto.getNaverId());
        processProfileImage(memberDto, file);

        Member member = createMemberEntity(memberDto);
        memberRepository.save(member);

        return MemberDto.Response.fromEntity(member);
    }

    @Transactional
    public MemberDto.Response updateMember(Long memberId, MemberDto.UpdateMemberRequest memberDto, MultipartFile file) {
        Member member = getMemberById(memberId);
        updateProfileImage(member, file);

        member.update(member.getProfileImage(), memberDto.getNickname(), memberDto.getFavoriteTeam());
        return MemberDto.Response.fromEntity(member, member.getProfileImage());
    }

    public MemberDto.Response getMember(Long memberId) {
        return MemberDto.Response.fromEntity(getMemberById(memberId));
    }

    private Member createMemberEntity(MemberDto.Request memberDto) {
        return Member.builder()
                .nickname(memberDto.getNickname())
                .profileImage(memberDto.getProfileImage())
                .favoriteTeam(memberDto.getFavoriteTeam())
                .memberCode(new CreateUuid().createUuid()) //TODO: uuid 객체 생성 방식 변경
                .age(memberDto.getAge())
                .name(memberDto.getName())
                .gender(memberDto.getGender())
                .naverId(memberDto.getNaverId())
                .build();
    }

    private Member getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(InvalidMemberIdException::new);
        if (member.isDeleted()) {
            throw new NotFoundMemberException();
        }
        return member;
    }

    private void validateDuplicateNaverId(String naverId) {
        if (memberRepository.existsByNaverId(naverId)) {
            throw new AlreadyExistsMemberException();
        }
    }

    private void processProfileImage(MemberDto.Request memberDto, MultipartFile file) {
        if (file != null) {
            String key = s3Service.uploadFile(file);
            memberDto.setProfileImage(s3Service.getFileUrl(key));
        }
    }

    private void updateProfileImage(Member member, MultipartFile file) {
        if (file != null) {
            if (member.getProfileImage() != null && !member.getProfileImage().isEmpty()) {
                s3Service.deleteFile(member.getProfileImage());
            }
            String key = s3Service.uploadFile(file);
            member.setProfileImage(s3Service.getFileUrl(key));
        }
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = getMemberById(memberId);
        if (member.getProfileImage() != null && !member.getProfileImage().isEmpty()) {
            s3Service.deleteFile(member.getProfileImage());
        }
        // 연관 데이터 익명화
        anonymizeMemberPosts(member);
        anonymizeMemberComments(member);

        // 본인 정보 익명화 및 탈퇴 처리
        member.anonymizeAndSoftDelete();
        memberRepository.save(member);
    }

    public void anonymizeMemberPosts(Member member) {
        List<Diary> diaries = diaryRepository.findAllByMember(member);
        for (Diary diary : diaries) {
            diary.setAuthorName("*****");
        }
        diaryRepository.saveAll(diaries);
    }

    public void anonymizeMemberComments(Member member) {
        List<Comment> comments = commentRepository.findAllByMember(member);
        for (Comment comment : comments) {
            comment.setWriterName("*****");
        }
        commentRepository.saveAll(comments);
    }
}