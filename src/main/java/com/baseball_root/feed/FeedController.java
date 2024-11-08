package com.baseball_root.feed;


import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    // 피드 상단 정렬된 친구리스트
    @GetMapping("/feeds/{memberId}/sorted-friend-list")
    public ResponseEntity<List<MemberDto>> getFeedSortedFriendList(@PathVariable(name = "memberId") Long memberId) {
        List<MemberDto> memberList = feedService.getFeedSortedFriendList(memberId);
        return ResponseEntity.ok(memberList);
    }
    // 내 피드를 모두 가져오기
    @GetMapping("/feeds/{memberId}/my-feed-list")
    public ResponseEntity<List<DiaryDto.Response>> getMyFeedList(@PathVariable(name = "memberId") Long memberId) {
        List<DiaryDto.Response> diaryList = feedService.getMyFeedList(memberId);
        return ResponseEntity.ok(diaryList);
    }
    //선택된 친구의 피드를 가져오기
    @GetMapping("/feeds/{memberId}/friend-feed-list")
    public ResponseEntity<List<DiaryDto.Response>> getFriendFeedList(@PathVariable(name = "memberId") Long memberId) {
        List<DiaryDto.Response> diaryList = feedService.getFriendFeedList(memberId);
        return ResponseEntity.ok(diaryList);
    }

    // 친구들의 피드를 모두 가져오기
    @GetMapping("/feeds/{memberId}/all")
    public ResponseEntity<List<DiaryDto.Response>> getAllFeedList(@PathVariable(name = "memberId") Long memberId,
                                                                  @RequestParam(name = "location", required = false) String location,
                                                                  @RequestParam(name = "gameDate", required = false) String gameDate,
                                                                  @RequestParam(name = "team", required = false) String team) {
        List<DiaryDto.Response> diaryList = feedService.getAllFeedList(memberId,location,gameDate,team);
        return ResponseEntity.ok(diaryList);
    }


}
