package com.baseball_root.diary.controller;

import com.baseball_root.diary.dto.ReactionDto;
import com.baseball_root.diary.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping("/diaries/reactions")
    public void saveDiaryOrCommentReaction(@RequestBody ReactionDto.Request reactionDto) {
        System.out.println("reactionDto : " + reactionDto.toString());
        reactionService.saveDiaryOrCommentReaction(reactionDto);
    }

}
