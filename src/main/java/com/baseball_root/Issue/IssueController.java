package com.baseball_root.Issue;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/my-pages/issues/{memberId}")
    public ResponseEntity<List<IssueDto.Response>> getIssueList(@PathVariable(name = "memberId") Long memberId) {
        List<IssueDto.Response> issueDtoList = issueService.getIssueList(memberId);
        return ResponseEntity.ok(issueDtoList);
    }
}
