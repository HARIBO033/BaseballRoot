package com.baseball_root.Issue;

import com.baseball_root.member.Member;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final MemberRepository memberRepository;

    public List<IssueDto.Response> getIssueList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("회원 ID를 찾을 수 없습니다: " + memberId)
        );
        List<Issue> issueList = issueRepository.findByReceiver(member);
        return issueList.stream()
                // 14일 이내의 이슈만 필터링
                .filter(issue -> issue.getCreatedAt().isAfter(LocalDateTime.now().minusDays(100))) // TODO: 테스트를 위해 100일로 설정 14일 이내로 바꿔야함
                .map(IssueDto.Response::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void markIssueAsRead(Long issueId) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(
                () -> new IllegalArgumentException("이슈 ID를 찾을 수 없습니다: " + issueId)
        );
        issue.setRead(true); // isRead 값을 true로 변경
    }
}
