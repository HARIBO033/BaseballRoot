package com.baseball_root.feed;


import com.baseball_root.diary.dto.DiaryDto;
import com.baseball_root.global.response.CommonResponse;
import com.baseball_root.global.response.SuccessCode;
import com.baseball_root.member.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FeedController {
    private final FeedService feedService;

    // 피드 상단 정렬된 친구리스트
    @GetMapping("/feeds/{memberId}/sorted-friend-list")
    public CommonResponse<List<MemberDto>> getFeedSortedFriendList(@PathVariable(name = "memberId") Long memberId) {
        List<MemberDto> memberList = feedService.getFeedSortedFriendList(memberId);
        log.info("getFeedSortedFriendList 호출 memberList = " + memberList);
        return CommonResponse.success(SuccessCode.GET_POST_LIST_SUCCESS, memberList);
    }

    // 내 피드를 모두 가져오기
    @GetMapping("/feeds/{memberId}/my-feed-list")
    public CommonResponse<Page<DiaryDto.Response>> getMyFeedList(@PathVariable(name = "memberId") Long memberId,
                                                                 @RequestParam(name = "location", required = false) String location,
                                                                 @RequestParam(name = "gameDate", required = false) String gameDate,
                                                                 @RequestParam(name = "team", required = false) String team,
                                                                 @PageableDefault(size = 5) Pageable pageable) {
        Page<DiaryDto.Response> diaryList = feedService.getMyFeedList(memberId,location,gameDate,team,pageable);
        log.info("getMyFeedList 호출 diaryList = " + diaryList);
        return CommonResponse.success(SuccessCode.GET_POST_LIST_SUCCESS, diaryList);
    }
    //선택된 친구의 피드를 가져오기
    @GetMapping("/feeds/{memberId}/friend-feed-list")
    public CommonResponse<Page<DiaryDto.Response>> getFriendFeedList(@PathVariable(name = "memberId") Long memberId,
                                                                     @RequestParam(name = "location", required = false) String location,
                                                                     @RequestParam(name = "gameDate", required = false) String gameDate,
                                                                     @RequestParam(name = "team", required = false) String team,
                                                                     @PageableDefault(size = 5) Pageable pageable) {
        Page<DiaryDto.Response> diaryList = feedService.getFriendFeedList(memberId,location,gameDate,team,pageable);
        log.info("getFriendFeedList 호출 diaryList = " + diaryList);
        return CommonResponse.success(SuccessCode.GET_POST_LIST_SUCCESS, diaryList);
    }

    // 친구들의 피드를 모두 가져오기
    @GetMapping("/feeds/{memberId}/all")
    public CommonResponse<Page<DiaryDto.Response>> getAllFeedList(@PathVariable(name = "memberId") Long memberId,
                                                                  @RequestParam(name = "location", required = false) String location,
                                                                  @RequestParam(name = "gameDate", required = false) String gameDate,
                                                                  @RequestParam(name = "team", required = false) String team,
                                                                  @PageableDefault(size = 5) Pageable pageable) {
        Page<DiaryDto.Response> diaryList = feedService.getAllFeedList(memberId,location,gameDate,team,pageable);
        log.info("getAllFeedList 호출 diaryList = " + diaryList);
        return CommonResponse.success(SuccessCode.GET_POST_LIST_SUCCESS, diaryList);
    }

}
