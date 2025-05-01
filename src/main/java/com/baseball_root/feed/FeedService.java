package com.baseball_root.feed;

import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.global.exception.custom_exception.InvalidMemberIdException;
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberDto;
import com.baseball_root.member.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final MemberRepository memberRepository;

    public FeedService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDto> getFeedSortedFriendList(Long memberId) {
        List<Member> friends = getMemberEntity(memberId).getFriends();
        friends.sort(null);

        return friends.stream().map(MemberDto::fromEntity).collect(Collectors.toList());
    }

    // 리스트를 Page로 변환
    private <T> Page<T> toPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<T> subList = list.subList(start, end);
        return new PageImpl<>(subList, pageable, list.size());
    }

    public Page<DiaryDto.Response> getMyFeedList(Long memberId, String location, String gameDate, String team, Pageable pageable) {
        List<Diary> diaryList = getMemberEntity(memberId).getDiaries();
        diaryList.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));

        List<DiaryDto.Response> filteredList = diaryList.stream()
                .filter(diary -> filterDiary(location, gameDate, team, diary) != null)
                .map(DiaryDto.Response::of)
                .collect(Collectors.toList());
        return toPage(filteredList, pageable);
    }

    public Page<DiaryDto.Response> getFriendFeedList(Long memberId, String location, String gameDate, String team, Pageable pageable) {
        List<Diary> diaryList = getMemberEntity(memberId).getDiaries();
        diaryList.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusDays(100);

        List<DiaryDto.Response> filteredList = diaryList.stream()
                .filter(diary -> diary.getCreatedAt().isAfter(twoWeeksAgo))
                .filter(diary -> filterDiary(location, gameDate, team, diary) != null)
                .map(diary -> filterDiary(location, gameDate, team, diary))
                .map(DiaryDto.Response::of)
                .collect(Collectors.toList());

        return toPage(filteredList, pageable);
    }

    public Page<DiaryDto.Response> getAllFeedList(Long memberId, String location, String gameDate, String team, Pageable pageable) { //TODO: 구단이름은 enum으로 관리하자
        List<Member> friends = getMemberEntity(memberId).getFriends();
        friends.sort(null);
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusDays(100);//TODO : 또 바뀐다고함
        List<Diary> combinedDiaryList = new ArrayList<>();
        List<Diary> myDiaryList = getMemberEntity(memberId).getDiaries().stream()
                .filter(diary -> diary.getCreatedAt().isAfter(twoWeeksAgo))
                .map(diary -> filterDiary(location, gameDate, team, diary))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        combinedDiaryList.addAll(myDiaryList);
        List<Diary> friendDiaryList = friends.stream()
                .map(Member::getDiaries).flatMap(List::stream)
                .filter(diary -> diary.getCreatedAt().isAfter(twoWeeksAgo))
                .map(diary -> filterDiary(location, gameDate, team, diary))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        combinedDiaryList.addAll(friendDiaryList);

        // 최신순 정렬
        combinedDiaryList.sort((d1, d2) -> d2.getCreatedAt().compareTo(d1.getCreatedAt()));
        List<DiaryDto.Response> filteredList = combinedDiaryList.stream()
                .map(DiaryDto.Response::of)
                .collect(Collectors.toList());
        return toPage(filteredList, pageable);
    }




    public Member getMemberEntity(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(InvalidMemberIdException::new);
    }

    public Diary filterDiary(String location, String gameDate, String team, Diary diary) {
        if (location != null && !location.equals(diary.getLocation()) && !location.equals("전체")) {
            return null;
        }
        if (gameDate != null && !gameDate.equals(diary.getGameDate().substring(0, 4)) && !gameDate.equals("전체")) {
            return null;
        }
        if (team != null && (!team.equals(diary.getHome()) && !team.equals(diary.getAway())) && !team.equals("전체")) {
            return null;
        }
        return diary;
    }


}
