package com.baseball_root.Issue;

import com.baseball_root.global.response.CommonResponse;
import com.baseball_root.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/my-pages/issues/{memberId}")
    public CommonResponse<List<IssueDto.Response>> getIssueList(@PathVariable(name = "memberId") Long memberId) {
        List<IssueDto.Response> issueDtoList = issueService.getIssueList(memberId);
        log.info("getIssueList 호출 issueDtoList = " + issueDtoList);
        return CommonResponse.success(SuccessCode.GET_ISSUE_LIST_SUCCESS, issueDtoList);
    }
}
