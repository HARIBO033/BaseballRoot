package com.baseball_root.diary.controller;

import com.baseball_root.diary.dto.ReactionDto;
import com.baseball_root.diary.service.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping("/diaries/reactions")
    public void saveDiaryOrCommentReaction(@RequestBody ReactionDto.Request reactionDto) {
        log.info("saveDiaryOrCommentReaction 호출 request : {}", reactionDto);
        reactionService.saveDiaryOrCommentReaction(reactionDto);
    }

}
