package com.baseball_root.feed;

import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberDto;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final MemberRepository memberRepository;

    public List<MemberDto> getFeedSortedFriendList(Long memberId) {
        List<Member> friends = getMember(memberId).getFriends();
        friends.sort(null);

        return friends.stream()
                .map(MemberDto::fromEntity)
                .collect(Collectors.toList());
    }
    public List<DiaryDto.Response> getMyFeedList(Long memberId){
        List<Diary> diaryList = getMember(memberId).getDiaries();
        diaryList.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        //7일 이내의 게시물만 가져오기
        return diaryList.stream()
                .filter(diary -> diary.getCreatedAt().isAfter(sevenDaysAgo))
                .map(DiaryDto.Response::of)
                .collect(Collectors.toList());
    }

    public List<DiaryDto.Response> getFriendFeedList(Long memberId) {
        List<Diary> diaryList = getMember(memberId).getDiaries();
        diaryList.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        //7일 이내의 게시물만 가져오기
        return diaryList.stream()
                .filter(diary -> diary.getCreatedAt().isAfter(sevenDaysAgo))
                .map(DiaryDto.Response::of)
                .collect(Collectors.toList());
    }


    public List<DiaryDto.Response> getAllFeedList(Long memberId, String location, String gameDate, String team) { //TODO: 구단이름은 enum으로 관리하자
        List<Member> friends = getMember(memberId).getFriends();
        friends.sort(null);
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Diary> diaryList = friends.stream()
                .map(Member::getDiaries)
                .flatMap(List::stream)
                .filter(diary -> diary.getCreatedAt().isAfter(sevenDaysAgo))
                .map(
                        diary -> {
                            if (location != null && !location.equals(diary.getLocation())) {
                                return null;
                            }
                            if (gameDate != null && !gameDate.equals(diary.getGameDate())) {
                                return null;
                            }
                            if (team != null && (!team.equals(diary.getHome()) && !team.equals(diary.getAway()))) {
                                return null;
                            }
                            return diary;
                        }
                )
                .collect(Collectors.toList());

        for (int i = 0; i < diaryList.size(); i++) {
            if (diaryList.get(i) == null) {
                diaryList.remove(i);
                i--;
            }
        }

        diaryList.sort((d1, d2) -> d2.getCreatedAt().compareTo(d1.getCreatedAt()));

        return diaryList.stream().map(DiaryDto.Response::of).collect(Collectors.toList());
    }
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
    }


}
